package com.user.invoicemanagement.model

import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.insert
import com.raizlabs.android.dbflow.kotlinextensions.list
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.*
import io.reactivex.Observable
import java.security.Timestamp


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

    override fun closeInvoice() {
        try {
            val factories = (select from ProductFactory::class).list
            val closedInvoice = ClosedInvoice()
            val oldFactories = mutableListOf<OldProductFactory>()

            factories.forEach { factory: ProductFactory? ->
                if (factory != null) {
                    val oldFactory = createOldFactory(factory)
                    oldFactory.products = createOldProducts(factory.products ?: emptyList())
                    oldFactories.add(oldFactory)

                    cleanProducts(factory.products ?: emptyList())
                }
            }

            closedInvoice.savedDate = System.currentTimeMillis()
            closedInvoice.insert()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAllClosedInvoices(): Observable<List<ClosedInvoice>> {
        val closedInvoices = (select from ClosedInvoice::class).list

        return Observable.fromArray(closedInvoices)
    }




    private fun createOldFactory(factory: ProductFactory): OldProductFactory {
        val oldFactory = OldProductFactory()
        oldFactory.name = factory.name

        return oldFactory
    }

    private fun createOldProducts(products: List<Product>): List<OldProduct> {
        val oldProducts = mutableListOf<OldProduct>()
        products.forEach { product: Product? ->
            if (product != null) {
                val oldProduct = OldProduct()
                oldProduct.name = product.name
                oldProduct.weightOnStore = product.weightOnStore
                oldProduct.weightInFridge = product.weightInFridge
                oldProduct.weightInStorage = product.weightInStorage
                oldProduct.weight4 = product.weight4
                oldProduct.weight5 = product.weight5
                oldProduct.purchasePrice = product.purchasePrice
                oldProduct.sellingPrice = product.sellingPrice

                oldProducts.add(oldProduct)
            }
        }
        return oldProducts
    }

    private fun cleanProducts(products: List<Product>) {
        products.forEach { product: Product? ->
            if (product != null) {
                product.weightOnStore = 0f
                product.weightInFridge = 0f
                product.weightInStorage = 0f
                product.weight4 = 0f
                product.weight5 = 0f

                product.update()
            }
        }
    }
}