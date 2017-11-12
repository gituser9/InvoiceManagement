package com.user.invoicemanagement.view.adapter.holder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main_footer.view.*


class ClosedInvoiceFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val mainFooterPurchaseSummary: TextView = itemView.mainFooterPurchaseSummary
    val mainFooterSellingSummary: TextView = itemView.mainFooterSellingSummary

}
