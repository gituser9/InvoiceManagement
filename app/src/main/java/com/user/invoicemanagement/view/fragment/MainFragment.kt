package com.user.invoicemanagement.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.presenter.MainPresenter
import com.user.invoicemanagement.view.adapter.FilterAutoCompleteAdapter
import com.user.invoicemanagement.view.adapter.MainSection
import com.user.invoicemanagement.view.fragment.dialog.EditFactoryDialogFragment
import com.user.invoicemanagement.view.fragment.dialog.SetWeightDialogFragment
import com.user.invoicemanagement.view.fragment.dialog.SummaryDialogFragment
import com.user.invoicemanagement.view.interfaces.MainView
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment(), MainView {

    lateinit var presenter: MainPresenter
    lateinit var adapter: SectionedRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        presenter = MainPresenter(this)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        adapter = SectionedRecyclerViewAdapter()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        presenter.getAll()

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filterView = activity.findViewById<AutoCompleteTextView>(R.id.tvFilter)
        filterView.visibility = View.VISIBLE
        filterView.setAdapter(FilterAutoCompleteAdapter(context, this))
        filterView.setOnItemClickListener { parent, view, position, id ->
            val product = parent.getItemAtPosition(position) as Product
            filterView.setText(product.name)
            filter(product.name)
        }

        activity.fab.visibility = View.VISIBLE
        activity.fab.setOnClickListener {
            saveAll(true)
        }
    }

    override fun onPause() {
        super.onPause()

        saveAll()
        activity.fab.visibility = View.GONE
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.add_manufacturer -> {
            saveAll()
            presenter.addNewFactory()
            true
        }
        R.id.show_summary -> {
            presenter.getSummary()
            true
        }
        R.id.close_invoice -> {
            presenter.closeInvoice()

            true
        }
        /*R.id.excel_export -> {
            presenter.exportToExcel(context)
            true
        }*/
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun showAll(list: List<ProductFactory>) {
        adapter.removeAllSections()

        if (list.isEmpty()) {
            return
        }

        val params = SectionParameters.Builder(R.layout.fragment_main_item)
                .headerResourceId(R.layout.fragment_main_header)
                .footerResourceId(R.layout.fragment_main_footer)
                .build()

        for (item in list) {
            val section = MainSection(params, item, item.products!!, this)
            adapter.addSection(section)
        }

        adapter.notifyDataSetChanged()
    }

    override fun addNewProduct(factoryId: Long) {
        presenter.addNewProduct(factoryId)
    }

    override fun deleteProduct(product: Product) {
        AlertDialog.Builder(context)
                .setIcon(R.drawable.ic_delete)
                .setTitle(R.string.deleting)
                .setMessage(String.format(activity.getString(R.string.delete_product), product.name))
                .setPositiveButton(R.string.delete) { _, _ -> presenter.deleteProduct(product.id) }
                .setNegativeButton(R.string.cancel, null)
                .show()
    }

    override fun updateProduct(product: Product) {
        presenter.updateProduct(product)
    }

    override fun deleteFactory(id: Long) {
        presenter.deleteFactory(id)
    }

    override fun showEditFactoryDialog(factory: ProductFactory) {
        val dialog = EditFactoryDialogFragment()
        dialog.oldName = factory.name
        dialog.positiveListener = DialogInterface.OnClickListener { _, _ ->
            presenter.updateFactory(dialog.edtFactoryName.text.toString(), factory.id)
            hideKeyboard()
        }
        dialog.show(activity.supportFragmentManager, "edit factory")
    }

    override fun showSummaryDialog(summary: Summary) {
        val bundle = Bundle()
        bundle.putFloat("purchaseSummary", summary.purchaseSummary)
        bundle.putFloat("sellingSummary", summary.sellingSummary)

        val dialog = SummaryDialogFragment()
        dialog.summary = summary
        dialog.arguments = bundle
        dialog.show(activity.supportFragmentManager, "summary")
    }

    override fun showSetWeightDialog(button: Button, product: Product, weightEnum: WeightEnum) {
        val dialog = SetWeightDialogFragment()
        dialog.button = button
        dialog.product = product
        dialog.weightEnum = weightEnum
        dialog.fragment = this

        dialog.show(activity.supportFragmentManager, "set weight")
    }

    override fun filter(name: String) {
        presenter.filter(name)
    }

    override fun getAll() {
        presenter.getAll()
    }

    override fun showWait() {
        recycler_view.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideWait() {
        progressBar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun saveAll(showMessage: Boolean) {
        val list = mutableListOf<Product>()

        for ((_, section) in adapter.sectionsMap) {
            val mainSection = section as MainSection
            list.addAll(mainSection.getViewData())
            mainSection.updateSummaryData()
        }

        presenter.saveAll(list, activity.getString(R.string.save_success), showMessage)
    }

}