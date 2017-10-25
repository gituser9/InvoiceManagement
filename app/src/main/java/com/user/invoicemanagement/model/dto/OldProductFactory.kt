package com.user.invoicemanagement.model.dto

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import java.util.*


class OldProductFactory {

    @PrimaryKey
    var id: Long = 0

    @Column
    var name: String? = null

    @Column
    var products: List<Product> = emptyList()

    @Column
    var savedDate: Date = Calendar.getInstance().time

}