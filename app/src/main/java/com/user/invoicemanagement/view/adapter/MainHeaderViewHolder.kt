package com.user.invoicemanagement.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_main_header.view.*


class MainHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val tvHeader: TextView = itemView.tvHeader
    val btnAddProduct: ImageButton = itemView.btnAddProduct
    val btnEditName: ImageButton = itemView.btnEditName
    val btnDelete: ImageButton = itemView.btnDelete

}