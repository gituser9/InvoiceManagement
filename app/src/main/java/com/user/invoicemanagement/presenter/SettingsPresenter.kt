package com.user.invoicemanagement.presenter

import android.content.Context
import android.os.Environment
import com.raizlabs.android.dbflow.config.FlowManager
import com.user.invoicemanagement.model.dto.DbflowDatabase
import com.user.invoicemanagement.view.fragment.SettingsFragment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class SettingsPresenter(val view: SettingsFragment) : BasePresenter() {

    fun moveDbFile(chosenPath: String, context: Context) {



        val db = FlowManager.getDatabase(DbflowDatabase.NAME).databaseFileName
        val path = "/${chosenPath.split(":").last()}/$db"

        val sd = Environment.getExternalStorageDirectory()
        val data = Environment.getDataDirectory()
        val currentDBPath = "/data/" + context.packageName + "/databases/" + db
        val currentDB = File(data, currentDBPath)
        val backupDB = File(sd, path)
        try {
            val source = FileInputStream(currentDB).channel
            val destination = FileOutputStream(backupDB).channel
            destination!!.transferFrom(source, 0, source!!.size())
            source.close()
            destination.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun replaceDb(newBasePath: String, context: Context) {
        val sd = Environment.getExternalStorageDirectory()
        val db = FlowManager.getDatabase(DbflowDatabase.NAME).databaseFileName
        val path = "$sd/${newBasePath.split(":").last()}"

        try {
            val data = Environment.getDataDirectory()
            val currentDBPath = "/data/" + context.packageName + "/databases/" + db
            val currentDB = File(data, currentDBPath)
            val newDB = File(path)

            if (currentDB.exists()) {
                val src = FileOutputStream(currentDB).channel
                val dst = FileInputStream(newDB).channel
                src.transferFrom(dst, 0, dst.size())
                src.close()
                dst.close()
            }
        } catch (e: Exception) {
            view.showToast("Upload database error")
        }
    }

}