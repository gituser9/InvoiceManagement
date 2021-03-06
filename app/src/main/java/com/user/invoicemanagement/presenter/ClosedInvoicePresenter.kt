package com.user.invoicemanagement.presenter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.view.fragment.ClosedInvoiceFragment
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
import java.text.SimpleDateFormat
import java.util.*


class ClosedInvoicePresenter(val view: ClosedInvoiceFragment) : BasePresenter() {

    fun getInvoice(invoiceId: Long) {
        model.getInvoice(invoiceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { invoice ->
                    view.show(invoice)
                }
    }

    fun exportToExcel(invoiceId: Long, context: Context) {
        if (invoiceId == 0L) {
            view.showToast(R.string.export_error)
            return
        }

        val preferences = context.getSharedPreferences(Constant.settingsName, Context.MODE_PRIVATE)
        val email = preferences.getString(Constant.emailForReportsKey, "")

        if (email.isEmpty()) {
            view.showAlert(R.string.email_is_required)
            return
        }

        model.getInvoice(invoiceId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { invoice: ClosedInvoice ->
                    createExcel(invoice)
                    sendEmail(context)
                }
    }

    private fun createExcel(invoice: ClosedInvoice): Boolean {
        val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale("ru"))
        val netDate = Date(invoice.savedDate)
        formattedDate.format(netDate)
        val filename = "Invoice-$formattedDate.xls"

        //Saving file in external storage
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.absolutePath + "/invoices")

        if (!directory.isDirectory) {
            directory.mkdirs()
        }

        val file = File(directory, filename)
        val wbSettings = WorkbookSettings()
        wbSettings.locale = Locale("en", "EN")

        val workbook: WritableWorkbook

        try {
            workbook = Workbook.createWorkbook(file, wbSettings)
            val sheet = workbook.createSheet("Invoice", 0)
            var currentRow = 0

            try {
                val factories = invoice.factories ?: emptyList()

                for (factory in factories) {
                    sheet.addCell(Label(0, currentRow, factory.name))
                    sheet.addCell(Label(8, currentRow, "Продажная"))
                    sheet.addCell(Label(9, currentRow, "Закупочная"))
                    ++currentRow

                    if (factory.products == null || factory.products!!.isEmpty()) {
                        currentRow += 2
                        continue
                    }

                    for (product in factory.products!!) {
                        sheet.addCell(Label(0, currentRow, product.name))
                        sheet.addCell(Label(1, currentRow, product.weightOnStore.toString()))
                        sheet.addCell(Label(2, currentRow, product.weightInFridge.toString()))
                        sheet.addCell(Label(3, currentRow, product.weightInStorage.toString()))
                        sheet.addCell(Label(4, currentRow, product.weight4.toString()))
                        sheet.addCell(Label(5, currentRow, product.weight5.toString()))
                        sheet.addCell(Label(6, currentRow, Constant.priceFormat.format(product.sellingPrice)))
                        sheet.addCell(Label(7, currentRow, Constant.priceFormat.format(product.purchasePrice)))
                        sheet.addCell(Label(8, currentRow, Constant.priceFormat.format(product.sellingPriceSummary)))
                        sheet.addCell(Label(9, currentRow, Constant.priceFormat.format(product.purchasePriceSummary)))

                        ++currentRow
                    }

                    ++currentRow
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
        val directory = File(sdCard.absolutePath + "/invoices")

        if (!directory.isDirectory) {
            directory.mkdirs()
        }

        val file = File(directory, filename)
        val path = Uri.fromFile(file)
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        // set the type to 'email'
        emailIntent.type = "vnd.android.cursor.dir/email"
        val preferences = context.getSharedPreferences(Constant.settingsName, Context.MODE_PRIVATE)
        val email = preferences.getString(Constant.emailForReportsKey, "")
        val to = arrayOf(email)
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)

        // the attachment
        emailIntent.putExtra(Intent.EXTRA_STREAM, path)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")

        context.startActivity(Intent.createChooser(emailIntent, "Send email..."))
    }

}