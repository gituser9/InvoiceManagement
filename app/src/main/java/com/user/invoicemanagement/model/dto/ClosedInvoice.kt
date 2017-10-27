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
class ClosedInvoice {

    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var savedDate: Long = 0

    @get:OneToMany(methods = arrayOf(OneToMany.Method.ALL))
    var factories by oneToMany { select from OldProductFactory::class where (OldProductFactory_Table.invoiceId.eq(id)) }
}