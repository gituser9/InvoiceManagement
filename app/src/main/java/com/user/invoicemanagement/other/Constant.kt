package com.user.invoicemanagement.other

import java.text.DecimalFormat
import java.text.NumberFormat


object Constant {

    const val UI_THREAD = "UI_THREAD"
    const val IO_THREAD = "IO_THREAD"
    const val priceFormat: String = "%.2f"
    const val maxFilterResults = 10
    const val emailForReportsKey = "emailForReports"
    const val settingsName = "userInfo"

    val baseFormat = NumberFormat.getCurrencyInstance()

    init {
        val decimalFormatSymbols = (baseFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        (baseFormat as DecimalFormat).decimalFormatSymbols = decimalFormatSymbols
    }
}