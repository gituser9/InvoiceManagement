package com.user.invoicemanagement.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.model.dto.OldProduct
import com.user.invoicemanagement.model.dto.OldProductFactory
import com.user.invoicemanagement.presenter.ClosedInvoicePresenter
import com.user.invoicemanagement.view.adapter.ClosedInvoiceSection
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_closed_invoice_list.*
import java.text.DecimalFormat
import java.text.NumberFormat


class ClosedInvoiceFragment : BaseFragment() {

    var invoiceId: Long = 0
    lateinit var adapter: SectionedRecyclerViewAdapter
    lateinit private var presenter: ClosedInvoicePresenter
    private val baseFormat = NumberFormat.getCurrencyInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ClosedInvoicePresenter(this)
        adapter = SectionedRecyclerViewAdapter()

        presenter.getInvoice(invoiceId)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_closed_invoice_list, container, false)
        val filterView = activity.findViewById<AutoCompleteTextView>(R.id.tvFilter)
        filterView.visibility = View.GONE
        val recyclerView = view.findViewById<RecyclerView>(R.id.closed_invoice_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter

        return view
    }

    fun show(invoice: ClosedInvoice) {
        val decimalFormatSymbols = (baseFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        (baseFormat as DecimalFormat).decimalFormatSymbols = decimalFormatSymbols

        var purchaseSummary = 0f
        var sellingSummary = 0f
        val params = SectionParameters.Builder(R.layout.fragment_closed_invoice_item)
                .headerResourceId(R.layout.fragment_closed_invoice_header)
                .footerResourceId(R.layout.fragment_closed_invoice_footer)
                .build()

        invoice.factories?.forEach { factory: OldProductFactory? ->
            if (factory != null) {
                factory.products?.forEach { product: OldProduct ->
                    purchaseSummary += product.purchasePriceSummary
                    sellingSummary += product.sellingPriceSummary
                }
                adapter.addSection(ClosedInvoiceSection(params, factory, factory.products ?: emptyList()))
            }
        }

        tvInvoiceSummary.text = activity.getString(R.string.total_summary) + ": ${baseFormat.format(sellingSummary)} - ${baseFormat.format(purchaseSummary)}"
        adapter.notifyDataSetChanged()
    }

    override fun filter(name: String) {

    }

    override fun getAll() {

    }
}
