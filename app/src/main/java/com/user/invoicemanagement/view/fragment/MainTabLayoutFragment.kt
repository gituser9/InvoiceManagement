package com.user.invoicemanagement.view.fragment


import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.view.*
import android.widget.AutoCompleteTextView
import com.user.invoicemanagement.R
import com.user.invoicemanagement.model.data.Summary
import com.user.invoicemanagement.model.dto.Product
import com.user.invoicemanagement.model.dto.ProductFactory
import com.user.invoicemanagement.presenter.MainTabLayoutPresenter
import com.user.invoicemanagement.view.adapter.FilterAutoCompleteAdapter
import com.user.invoicemanagement.view.fragment.dialog.AddFactoryDialogFragment
import com.user.invoicemanagement.view.fragment.dialog.EditFactoryDialogFragment
import com.user.invoicemanagement.view.fragment.dialog.SummaryDialogFragment
import com.user.invoicemanagement.view.interfaces.MainTabLayoutView
import kotlinx.android.synthetic.main.fragment_main_tab_layout.*


class MainTabLayoutFragment : BaseFragment(), MainTabLayoutView {

    private val factoryMap = mutableMapOf<Int, ProductFactory>()
    private lateinit var layoutPresenter: MainTabLayoutPresenter
    private lateinit var currentFactory: ProductFactory


    /**
     * Lifecycle
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        layoutPresenter = MainTabLayoutPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main_tab_layout, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutPresenter.getAll()

        val filterView = activity.findViewById<AutoCompleteTextView>(R.id.tvFilter)
        filterView.visibility = View.VISIBLE
        filterView.setAdapter(FilterAutoCompleteAdapter(context, this))
        filterView.setOnItemClickListener { parent, view, position, id ->
            val product = parent.getItemAtPosition(position) as Product
            filterView.setText(product.name)
            layoutPresenter.filter(product.name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_tab, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.add_manufacturer -> {
            showAddFactoryDialog()
            true
        }
        R.id.update_factory -> {
            showEditFactoryDialog(currentFactory)
            true
        }
        R.id.delete_factory -> {
            layoutPresenter.deleteFactory(currentFactory.id, mainTabLayout.selectedTabPosition)
            true
        }
        R.id.show_summary -> {
            layoutPresenter.getSummary()
            true
        }
        R.id.close_invoice -> {
            layoutPresenter.closeInvoice()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    /**
     * MainTabLayoutView
     */
    override fun setupTabs(factories: List<ProductFactory>) {
        mainTabLayout.removeAllTabs()

        for ((index, item) in factories.withIndex()) {
            mainTabLayout.addTab(mainTabLayout.newTab().setText(item.name), index)
            factoryMap.put(index, item)
        }
        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                replaceFragment(tab.position)

                currentFactory = factoryMap[tab.position] ?: ProductFactory()
            }
        })
        if (!factoryMap.isEmpty()) {
            currentFactory = factoryMap[0] ?: ProductFactory()
            replaceFragment(0)
        }
    }

    override fun showSummaryDialog(summary: Summary) {
        val bundle = Bundle()
        bundle.putFloat("purchaseSummary", summary.purchaseSummary)
        bundle.putFloat("sellingSummary", summary.sellingSummary)

        val dialog = SummaryDialogFragment()
        dialog.summary = summary
        dialog.arguments = bundle
        dialog.show(activity.supportFragmentManager, "summary")
    }

    override fun addTab(factory: ProductFactory) {
        val index = factoryMap.size
        mainTabLayout.addTab(mainTabLayout.newTab().setText(factory.name), index)
        factoryMap.put(index, factory)

        replaceFragment(index)
        mainTabLayout.getTabAt(index)?.select()
    }

    override fun removeTab(position: Int) {
        mainTabLayout.removeTabAt(position)
        factoryMap.remove(position)
    }

    override fun showAddFactoryDialog() {
        val dialog = AddFactoryDialogFragment()
        dialog.positiveListener = DialogInterface.OnClickListener { _, _ ->
            layoutPresenter.addNewFactory(dialog.edtFactoryName.text.toString())
        }
        dialog.show(activity.supportFragmentManager, "add factory")
    }

    override fun showEditFactoryDialog(factory: ProductFactory) {
        val dialog = EditFactoryDialogFragment()
        dialog.oldName = factory.name
        dialog.positiveListener = DialogInterface.OnClickListener { _, _ ->
            layoutPresenter.updateFactory(dialog.edtFactoryName.text.toString(), factory.id)
            hideKeyboard()
        }
        dialog.show(activity.supportFragmentManager, "edit factory")
    }

    override fun setTabTitle(title: String) {
        val tab = mainTabLayout.getTabAt(mainTabLayout.selectedTabPosition)
        tab?.text = title
    }




    private fun replaceFragment(position: Int) {
        val fragment = MainTabFragment()
        fragment.factory = factoryMap[position] ?: ProductFactory()

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.tabContainer, fragment, "tab")
        transaction.commit()
    }


    override fun filter(name: String) {
        layoutPresenter.filter(name)
    }
    override fun getAll() {
        layoutPresenter.getAll()
    }

}
