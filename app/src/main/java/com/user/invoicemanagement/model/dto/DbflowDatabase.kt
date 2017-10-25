package com.user.invoicemanagement.model.dto

import com.raizlabs.android.dbflow.annotation.Database


@Database(name = "InvoiceManagement.db", version = 1)
class DbflowDatabase {

    companion object {
        @JvmStatic val NAME = "InvoiceManagement.db"
        @JvmStatic val VERSION = 1
    }

}