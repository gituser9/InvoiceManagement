package com.user.invoicemanagement.model.dto

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.OneToMany
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.oneToMany
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import java.util.*


@Table(database = DbflowDatabase::class)
class OldProductFactory {

    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var invoiceId: Long = 0

    @Column
    var name: String? = null

    @get:OneToMany(methods = arrayOf(OneToMany.Method.ALL))
    var products by oneToMany { select from OldProduct::class where (OldProduct_Table.factoryId.eq(id)) }

}