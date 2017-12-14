package com.user.invoicemanagement.view.interfaces

import android.widget.Button
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory


interface MainView : BaseView, Saver {

    fun showAll(list: List<ProductFactory>)

    fun addNewProduct(factoryId: Long)

    fun deleteProduct(product: Product)

    fun updateProduct(product: Product)

    fun deleteFactory(id: Long)

    fun showEditFactoryDialog(factory: ProductFactory)

    fun showSummaryDialog(summary: Summary)

    fun showSetWeightDialog(button: Button, product: Product, weightEnum: WeightEnum)

    fun showWait()

    fun hideWait()

}