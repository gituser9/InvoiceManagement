package com.user.invoicemanagement.model

import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.model.dto.Product_Table
import io.reactivex.Observable


class ModelImpl : Model {


    override fun getAll(): Observable<List<ProductFactory>> {
        val results = (select from ProductFactory::class).list

        return Observable.fromArray(results)
    }

    override fun addNewFactory(name: String) {
//        val results = (select from ProductFactory::class).list
//        results.forEach { item -> item.delete() }
        val newFactory = ProductFactory()
        newFactory.name = name

        newFactory.insert()
    }

    override fun addNewProduct(factoryId: Long) {
        try {
            val product = Product()
            product.factoryId = factoryId

            product.insert()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun deleteProduct(id: Long) {
        val result = (select from Product::class where (Product_Table.id.eq(id))).result
        result?.delete()
    }
}