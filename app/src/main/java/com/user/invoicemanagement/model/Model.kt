package com.user.invoicemanagement.model

import com.user.invoicemanagement.model.dto.ProductFactory
import io.reactivex.Observable


interface Model {

    fun getAll() : Observable<List<ProductFactory>>

    fun addNewFactory(name: String)

    fun addNewProduct(factoryId: Long)

    fun deleteProduct(id: Long)




}