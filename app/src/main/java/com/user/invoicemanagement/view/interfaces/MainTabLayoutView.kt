package com.user.invoicemanagement.view.interfaces

import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.ProductFactory


interface MainTabLayoutView : BaseView {

    fun setupTabs(factories: List<ProductFactory>)

    fun showSummaryDialog(summary: Summary)

    fun addTab(factory: ProductFactory)

    fun removeTab(position: Int)

    fun showAddFactoryDialog()

    fun showEditFactoryDialog(factory: ProductFactory)

    fun setTabTitle(title: String)

}