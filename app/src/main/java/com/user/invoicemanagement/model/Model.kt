package com.user.invoicemanagement.model

import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.model.dto.OldProductFactory
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import io.reactivex.Observable


interface Model {

    fun getAll() : Observable<List<ProductFactory>>

    fun addNewFactory(name: String)

    fun addNewProduct(factoryId: Long)

    fun deleteProduct(id: Long)

    fun updateProduct(product: Product)

    fun deleteFactory(id: Long)

    fun updateFactory(newName: String, factoryId: Long)

    fun getSummary(): Observable<Summary>

    fun closeInvoice()

    fun getAllClosedInvoices(): Observable<List<ClosedInvoice>>
}