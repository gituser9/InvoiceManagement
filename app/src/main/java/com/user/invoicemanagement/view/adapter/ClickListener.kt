package com.user.invoicemanagement.view.adapter

import android.view.View
import com.user.invoicemanagement.model.dto.ClosedInvoice


interface ArchiveClickListener {
    fun onClick(invoice: ClosedInvoice)
}