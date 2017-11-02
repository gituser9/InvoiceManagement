package com.user.invoicemanagement.model.dto

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table


@Table(database = DbflowDatabase::class)
class OldProduct {

    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column
    var factoryId: Long = 0

    @Column
    var name: String = ""

    @Column
    var weightOnStore: Float = 0f

    @Column
    var weightInFridge: Float = 0f

    @Column
    var weightInStorage: Float = 0f

    @Column
    var weight4: Float = 0f

    @Column
    var weight5: Float = 0f

    @Column
    var purchasePrice: Float = 0f

    @Column
    var sellingPrice: Float = 0f



    var purchasePriceSummary: Float = 0f
        get() = (weightOnStore + weightInFridge + weightInStorage + weight4 + weight5) * purchasePrice

    var sellingPriceSummary: Float = 0f
        get() = (weightOnStore + weightInFridge + weightInStorage + weight4 + weight5) * sellingPrice


}