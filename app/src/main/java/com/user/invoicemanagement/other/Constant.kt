package com.user.invoicemanagement.other

import java.text.DecimalFormat
import java.text.NumberFormat


object Constant {

    const val emailForReportsKey = "emailForReports"
    const val settingsName = "userInfo"

    val priceFormat = NumberFormat.getCurrencyInstance()
    val whiteSpaceRegex: Regex = "\\s".toRegex()

    init {
        val decimalFormatSymbols = (priceFormat as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        priceFormat.decimalFormatSymbols = decimalFormatSymbols
    }
}