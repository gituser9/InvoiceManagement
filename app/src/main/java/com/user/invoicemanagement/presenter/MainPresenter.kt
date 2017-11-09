package com.user.invoicemanagement.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.view.fragment.MainView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException
import java.io.File
import java.io.IOException
import java.util.*


class MainPresenter(var view: MainView) : BasePresenter() {

    init {
        model = ModelImpl()
    }

    fun getAll() {
        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    view.showAll(factoryList)
                }
    }

    fun addNewFactory() {
        model.addNewFactory("New")

        getAll()
    }

    fun addNewProduct(factoryId: Long) {
        val subscription = model.addNewProduct(factoryId) ?: return

        subscription
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                getAll()
            }
    }

    fun deleteProduct(id: Long) {
        model.deleteProduct(id)

        getAll()
    }

    fun updateProduct(product: Product) {
        val subscription = model.updateProduct(product) ?: return
    }

    fun deleteFactory(id: Long) {
        model.deleteFactory(id)

        getAll()
    }

    fun updateFactory(newName: String, factoryId: Long) {
        model.updateFactory(newName, factoryId)

        getAll()
    }

    fun getSummary() {
        model.getSummary()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { summary ->
                    view.showSummaryDialog(summary)
                }
    }

    fun closeInvoice() {
        model.closeInvoice()
        getAll()
    }

    fun filter(name: String) {
        model.filterProducts(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    view.showAll(factoryList)
                }
    }

    fun exportToExcel(context: Context) {
        view.showWait()

        model.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { factoryList ->
                    if (!createExcel(factoryList)) {
                        view.hideWait()
                        return@subscribe
                    }

                    sendEmail(context)

                    view.hideWait()
                }
    }

    private fun createExcel(factories: List<ProductFactory>): Boolean {
        val filename = "Invoice.xls"

        //Saving file in external storage
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.getAbsolutePath() + "/invoices")

        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs()
        }

        val file = File(directory, filename)
        val wbSettings = WorkbookSettings()
        wbSettings.locale = Locale("en", "EN")

        val workbook: WritableWorkbook

        try {
            workbook = Workbook.createWorkbook(file, wbSettings)
            val sheet = workbook.createSheet("Invoice", 0)
            var productRows = 0

            try {
                for ((factoryIndex, factory) in factories.withIndex()) {
                    sheet.addCell(Label(0, factoryIndex + productRows, factory.name))
                    ++productRows

                    for ((productIndex, product) in factory.products!!.withIndex()) {
                        sheet.addCell(Label(0, productIndex + productRows, product.name))
                        sheet.addCell(Label(1, productIndex + productRows, product.weightOnStore.toString()))
                        sheet.addCell(Label(2, productIndex + productRows, product.weightInFridge.toString()))
                        sheet.addCell(Label(3, productIndex + productRows, product.weightInStorage.toString()))
                        sheet.addCell(Label(4, productIndex + productRows, product.weight4.toString()))
                        sheet.addCell(Label(5, productIndex + productRows, product.weight5.toString()))
                        sheet.addCell(Label(6, productIndex + productRows, product.sellingPrice.toString()))
                        sheet.addCell(Label(7, productIndex + productRows, product.purchasePrice.toString()))
                        sheet.addCell(Label(8, productIndex + productRows, product.sellingPriceSummary.toString()))
                        sheet.addCell(Label(9, productIndex + productRows, product.purchasePriceSummary.toString()))
                    }

                    productRows += factory.products?.size ?: 0
                }
            } catch (e: RowsExceededException) {
                return false
            } catch (e: WriteException) {
                return false
            }

            workbook.write()

            try {
                workbook.close()
            } catch (e: WriteException) {
                return false
            }
        } catch (e: IOException) {
            return false
        }

        return true
    }

    private fun sendEmail(context: Context) {
        val filename = "Invoice.xls"
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.getAbsolutePath() + "/invoices")

        if (!directory.isDirectory()) {
            directory.mkdirs()
        }

        val file = File(directory, filename)
        val path = Uri.fromFile(file)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // set the type to 'email'
        emailIntent.type = "vnd.android.cursor.dir/email"
        val to = arrayOf("jobmail862@gmail.com")
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)

        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")

        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

    fun saveAll(products: List<Product>, successMessage: String, showMessage: Boolean) {
        Observable.fromArray(products)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list: List<Product> ->
                    model.saveAll(list)

                    if (showMessage) {
                        view.showToast(successMessage)
                    }
                }
    }

}