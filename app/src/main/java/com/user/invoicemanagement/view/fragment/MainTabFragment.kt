package com.user.invoicemanagement.view.fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.WeightEnum
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.presenter.MainTabPresenter
import com.user.invoicemanagement.view.adapter.MainTabRecyclerViewAdapter
import com.user.invoicemanagement.view.fragment.dialog.SetWeightDialogFragment
import com.user.invoicemanagement.view.interfaces.Saver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main_tab_item_list.*


class MainTabFragment : BaseFragment(), Saver {

    lateinit var factory: ProductFactory
    lateinit var adapter: MainTabRecyclerViewAdapter
    lateinit var presenter: MainTabPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        presenter = MainTabPresenter(this)
        adapter = MainTabRecyclerViewAdapter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_main_tab_item_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.mainTabRecyclerView)
        recyclerView.adapter = adapter

        activity.fab.visibility = View.VISIBLE
        activity.fab.setOnClickListener {
            saveAll(true)
        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFactory(factory)

        btnAddNew.setOnClickListener {
            presenter.addNewProduct(factory.id)
        }
    }

    override fun onPause() {
        super.onPause()

        saveAll()
    }



    fun showFactory(productFactory: ProductFactory) {
        val list = mutableListOf<Product>()
        list.addAll(0, productFactory.products ?: emptyList())

        setSummary(productFactory)
        adapter.items = list
        adapter.notifyDataSetChanged()
    }

    override fun saveAll(showMessage: Boolean) {
        val list = adapter.getViewData()
        presenter.saveAll(list, activity.getString(R.string.save_success), showMessage)
        adapter.updateSummaryData()
        setSummary(factory)
    }

    fun showSetWeightDialog(button: Button, product: Product, weightEnum: WeightEnum) {
        val dialog = SetWeightDialogFragment()
        dialog.button = button
        dialog.product = product
        dialog.weightEnum = weightEnum
        dialog.fragment = this

        dialog.show(activity.supportFragmentManager, "set weight")
    }

    fun addProduct(product: Product) {
        adapter.items.add(product)
        adapter.notifyItemChanged(adapter.items.size - 1)
    }

    fun deleteProductFromView(index: Int) {
        adapter.items.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    fun deleteProduct(product: Product, position: Int) {
        AlertDialog.Builder(context)
                .setIcon(R.drawable.ic_delete)
                .setTitle(R.string.deleting)
                .setMessage(String.format(activity.getString(R.string.delete_product), product.name))
                .setPositiveButton(R.string.delete) { _, _ ->
                    presenter.deleteProduct(product.id, position)
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
    }


    private fun setSummary(factory: ProductFactory) {
        var purchaseSummary = 0f
        var sellingSummary = 0f

        factory.products?.forEach { product: Product ->
            purchaseSummary += product.purchasePriceSummary
            sellingSummary += product.sellingPriceSummary
        }

        mainTabSummary.text = activity.getString(R.string.total_summary) + ": ${Constant.priceFormat.format(sellingSummary)} - ${Constant.priceFormat.format(purchaseSummary)}"
    }



    override fun filter(name: String) {
    }

    override fun getAll() {
    }


}
