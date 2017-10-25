package com.user.invoicemanagement.model.dto

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.OneToMany
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.oneToMany
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where


@Table(database = DbflowDatabase::class)
class ProductFactory {

    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var name: String = ""


    @get:OneToMany(methods = arrayOf(OneToMany.Method.ALL))
    var products by oneToMany { select from Product::class where (Product_Table.factoryId.eq(id)) }
}