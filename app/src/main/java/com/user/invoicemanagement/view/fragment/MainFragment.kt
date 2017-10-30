package com.user.invoicemanagement.view.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.View
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.presenter.MainPresenter
import com.user.invoicemanagement.view.adapter.MainSection
import com.user.invoicemanagement.view.fragment.dialog.EditFactoryDialogFragment
import com.user.invoicemanagement.view.fragment.dialog.SummaryDialogFragment
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


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


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.add_manufacturer -> {
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
            adapter.addSection(MainSection(params, item, item.products!!, this))
        }

        adapter.notifyDataSetChanged()
    }

    override fun addNewProduct(factoryId: Long) {
        presenter.addNewProduct(factoryId)
    }

    override fun deleteProduct(id: Long) {
        presenter.deleteProduct(id)
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



}