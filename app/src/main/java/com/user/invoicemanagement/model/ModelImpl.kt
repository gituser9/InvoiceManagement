package com.user.invoicemanagement.model

import com.raizlabs.android.dbflow.kotlinextensions.*
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.*
import io.reactivex.Observable


class ModelImpl : Model {

    override fun getAll(): Observable<List<ProductFactory>> {
        val results = (select from ProductFactory::class).list

        return Observable.fromArray(results)
    }

    override fun addNewFactory(name: String) {
        val newFactory = ProductFactory()
        newFactory.name = name

        newFactory.insert()
    }

    override fun addNewProduct(factoryId: Long): Observable<Product>? {
        return try {
            val product = Product()
            product.factoryId = factoryId

//            product.insert()
            product.save()

            Observable.just(product)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun deleteProduct(id: Long) {
        val result = (select from Product::class where (Product_Table.id.eq(id))).result
        result?.delete()
    }

    override fun updateProduct(product: Product): Observable<Product>? {
        val result = (select from Product::class where (Product_Table.id.eq(product.id))).result ?: return null

        result.name = product.name
        result.weightOnStore = product.weightOnStore
        result.weightInFridge = product.weightInFridge
        result.weightInStorage = product.weightInStorage
        result.weight4 = product.weight4
        result.weight5 = product.weight5
        result.purchasePrice = product.purchasePrice
        result.sellingPrice = product.sellingPrice

        result.update()

        return Observable.just(result)
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
            val sellingPriceSummary = (product.weightOnStore + product.weightInFridge + product.weightInStorage + product.weight4 + product.weight5) * product.sellingPrice
            val purchasePriceSummary = (product.weightOnStore + product.weightInFridge + product.weightInStorage + product.weight4 + product.weight5) * product.purchasePrice


            purchaseSummary += purchasePriceSummary
            sellingSummary += sellingPriceSummary
        }

        return Observable.just(Summary(purchaseSummary, sellingSummary))
    }

    override fun closeInvoice() {
        try {
            val factories = (select from ProductFactory::class).list
            val closedInvoice = ClosedInvoice()
            closedInvoice.savedDate = System.currentTimeMillis()
            closedInvoice.insert()
            val oldFactories = mutableListOf<OldProductFactory>()


            for (factory in factories) {
                val oldFactory = createOldFactory(factory)
                oldFactory.invoiceId = closedInvoice.id
                oldFactory.insert()
                oldFactories.add(oldFactory)

                createOldProducts(factory.products ?: emptyList(), oldFactory.id)
                cleanProducts(factory.products ?: emptyList())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAllClosedInvoices(): Observable<List<ClosedInvoice>> {
        val closedInvoices = (select from ClosedInvoice::class).list

        return Observable.fromArray(closedInvoices)
    }

    override fun deleteInvoice(invoice: ClosedInvoice) {
        invoice.delete()
    }

    override fun getInvoice(invoiceId: Long): Observable<ClosedInvoice> {
        val result = (select from ClosedInvoice::class where (ClosedInvoice_Table.id.eq(invoiceId))).result

        return Observable.just(result)
    }

    override fun searchProducts(name: String): List<Product> {
        val products = (select from Product::class where (Product_Table.name.like("%$name%"))).list
        return products
    }

    override fun filterProducts(name: String): Observable<List<ProductFactory>> {
        val products = (select from Product::class where (Product_Table.name.like("%$name%"))).list
        val ids: MutableList<Long> = mutableListOf()

        for (product in products) {
            ids.add(product.factoryId)
        }

        val results = (select from ProductFactory::class where (ProductFactory_Table.id.`in`(ids))).list

        for (factory in results) {
            val filteredProducts: MutableList<Product> = mutableListOf()

            for (product in factory.products!!) {
                if (product.name.contains(name, true)) {
                    filteredProducts.add(product)
                }
            }

            factory.products = filteredProducts
        }

        return Observable.fromArray(results)
    }

    override fun saveAll(products: List<Product>) {
        for (item in products) {
            item.save()
        }
    }




    private fun createOldFactory(factory: ProductFactory): OldProductFactory {
        val oldFactory = OldProductFactory()
        oldFactory.name = factory.name

        return oldFactory
    }

    private fun createOldProducts(products: List<Product>, factoryId: Long): List<OldProduct> {
        val oldProducts = mutableListOf<OldProduct>()

        for (product in products) {
            val oldProduct = OldProduct()
            oldProduct.factoryId = factoryId
            oldProduct.name = product.name
            oldProduct.weightOnStore = product.weightOnStore
            oldProduct.weightInFridge = product.weightInFridge
            oldProduct.weightInStorage = product.weightInStorage
            oldProduct.weight4 = product.weight4
            oldProduct.weight5 = product.weight5
            oldProduct.purchasePrice = product.purchasePrice
            oldProduct.sellingPrice = product.sellingPrice

            oldProduct.insert()

            oldProducts.add(oldProduct)
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