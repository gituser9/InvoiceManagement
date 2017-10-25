package com.user.invoicemanagement.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.View
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.presenter.MainPresenter
import com.user.invoicemanagement.view.MainActivityCallback
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter


class MainFragment : BaseFragment(), MainView {

    lateinit var presenter: MainPresenter
    lateinit var adapter: SectionedRecyclerViewAdapter
    lateinit var activityCallback: MainActivityCallback


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        presenter = MainPresenter(this)
//        activityCallback = activity.
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        adapter = SectionedRecyclerViewAdapter()

        if (savedInstanceState == null) {
            presenter.getAll()
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

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
                .build()

        for (item in list) {
            adapter.addSection(Section(params, item, item.products!!, this))
        }

        adapter.notifyDataSetChanged()
    }

    override fun addNewProduct(factoryId: Long) {
        presenter.addNewProduct(factoryId)
    }

    override fun deleteProduct(id: Long) {
        presenter.deleteProduct(id)
    }

}