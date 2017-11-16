package com.user.invoicemanagement.other

import java.text.DecimalFormat
import java.text.NumberFormat


object Constant {

    const val emailForReportsKey = "emailForReports"
    const val settingsName = "userInfo"

    val baseFormat = NumberFormat.getCurrencyInstance()

    init {
        val decimalFormatSymbols = (baseFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        baseFormat.decimalFormatSymbols = decimalFormatSymbols
    }
}