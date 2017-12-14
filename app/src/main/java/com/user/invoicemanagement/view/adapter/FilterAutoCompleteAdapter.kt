package com.user.invoicemanagement.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.ModelImpl
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.view.interfaces.BaseView


class FilterAutoCompleteAdapter(private val context: Context, private val view: BaseView) : BaseAdapter(), Filterable {

    val model = ModelImpl.instance
    var searchResults: List<Product> = emptyList()


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView

        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.simple_dropdown_item, parent, false)
        }

        val product = getItem(position) as Product
        view?.findViewById<TextView>(R.id.productName)?.text = product.name

        return view!!
    }

    override fun getItem(position: Int): Any = searchResults[position]

    override fun getItemId(position: Int): Long = searchResults[position].id

    override fun getCount(): Int = searchResults.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if (constraint != null) {
                    val products = findProducts(constraint.toString())
                    filterResults.values = products
                    filterResults.count = products.size

                    view.filter(constraint.toString())
                } else {
                    view.getAll()
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results?.values != null) {
                    searchResults = results.values as List<Product>
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }
        }
    }

    private fun findProducts(name: String): List<Product> = model.searchProducts(name)

}