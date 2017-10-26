package com.user.invoicemanagement.model

import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.SQLite
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.model.dto.ProductFactory_Table
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

    override fun updateProduct(product: Product) {
        val result = (select from Product::class where (Product_Table.id.eq(product.id))).result ?: return

        result.name = product.name
        result.weightOnStore = product.weightOnStore
        result.weightInFridge = product.weightInFridge
        result.weightInStorage = product.weightInStorage
        result.weight4 = product.weight4
        result.weight5 = product.weight5
        result.purchasePrice = product.purchasePrice
        result.sellingPrice = product.sellingPrice

        result.update()
    }

    override fun deleteFactory(id: Long) {
        val products = (select from Product::class where Product_Table.factoryId.eq(id)).list
        products.forEach { item -> item.delete() }

        val factory = (select from ProductFactory::class where (ProductFactory_Table.id.eq(id))).result
        factory?.delete()
    }

    override fun updateFactory(newName: String, factoryId: Long) {
        val factory = (select from ProductFactory::class where (ProductFactory_Table.id.eq(factoryId))).result
        factory?.name = newName
        factory?.update()
    }

    override fun getSummary(): Observable<Summary> {
        var purchaseSummary = 0f
        var sellingSummary = 0f
        val products = (select from Product::class).list

        products.forEach { product: Product ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        return Observable.just(Summary(purchaseSummary, sellingSummary))
    }
}