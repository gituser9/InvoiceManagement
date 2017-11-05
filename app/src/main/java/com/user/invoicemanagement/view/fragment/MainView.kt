package com.user.invoicemanagement.view.fragment

import android.widget.Button
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.view.adapter.holder.MainViewHolder


interface MainView : BaseView {

    fun showAll(list: List<ProductFactory>)

    fun addNewProduct(factoryId: Long)

    fun deleteProduct(id: Long)

    fun updateProduct(product: Product)

    fun deleteFactory(id: Long)

    fun showEditFactoryDialog(factory: ProductFactory)

    fun showSummaryDialog(summary: Summary)

    fun showSetWeightDialog(button: Button)

}