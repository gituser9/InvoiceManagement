package com.user.invoicemanagement.other

import android.app.Application
import com.raizlabs.android.dbflow.config.FlowManager


open class App : Application() {


    override fun onCreate() {
        super.onCreate()

        FlowManager.init(this)
//        FlowManager.destroy()
    }

}