package com.user.invoicemanagement.view.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.model.dto.OldProductFactory
import com.user.invoicemanagement.presenter.ArchivePresenter
import com.user.invoicemanagement.view.adapter.ArchiveAdapter
import com.user.invoicemanagement.view.adapter.ArchiveClickListener
import kotlinx.android.synthetic.main.fragment_archive.*


class ArchiveFragment : Fragment() {

    lateinit var adapter: ArchiveAdapter
    lateinit var presenter: ArchivePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(false)

        presenter = ArchivePresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_archive, container, false)

        adapter = ArchiveAdapter()
        adapter.deleteClickListener = object : ArchiveClickListener {
            override fun onClick(invoice: ClosedInvoice) {
                presenter.deleteInvoice(invoice)
            }
        }
        adapter.openClickListener = object : ArchiveClickListener {
            override fun onClick(invoice: ClosedInvoice) {
                showClosedIvoice(invoice.id)
            }
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_archive)
        recyclerView.layoutManager = LinearLayoutManager(activity.baseContext, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        presenter.getAll()

        return view
    }

    fun showAll(closedInvoices: List<ClosedInvoice>) {
        adapter.list = closedInvoices
        adapter.notifyDataSetChanged()
    }


    private fun showClosedIvoice(id: Long) {
        val fragment = ClosedInvoiceFragment()
        fragment.invoiceId = id
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, "Archive")
        transaction.addToBackStack("Archive")
        transaction.commit()
    }
}
