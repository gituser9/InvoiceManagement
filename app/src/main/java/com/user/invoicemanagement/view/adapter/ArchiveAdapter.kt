package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.dto.ClosedInvoice
import com.user.invoicemanagement.model.dto.OldProductFactory
import kotlinx.android.synthetic.main.card_archive.view.*
import java.text.SimpleDateFormat
import java.util.*


class ArchiveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var tvInvoiceDate: TextView = itemView.tvInvoiceDate

}

class ArchiveAdapter : RecyclerView.Adapter<ArchiveViewHolder>() {

    var list = emptyList<ClosedInvoice>()

    override fun onBindViewHolder(holder: ArchiveViewHolder, position: Int) {
        val closedInvoices = list[position]
        holder.tvInvoiceDate.text = getDate(closedInvoices.savedDate)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchiveViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_archive, parent, false)

        return ArchiveViewHolder(view)
    }


    private fun getDate(timeStamp: Long): String {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("ru"))
            val netDate = Date(timeStamp)
            sdf.format(netDate)
        } catch (ex: Exception) {
            "xx"
        }
    }
}