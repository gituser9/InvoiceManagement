package com.user.invoicemanagement.view.fragment

import com.user.invoicemanagement.model.dto.ProductFactory


interface MainView : View {

    fun showAll(list: List<ProductFactory>)

    fun addNewProduct(factoryId: Long)

    fun deleteProduct(id: Long)

}