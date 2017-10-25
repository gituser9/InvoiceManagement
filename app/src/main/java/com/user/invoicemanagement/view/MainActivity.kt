package com.user.invoicemanagement.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.user.invoicemanagement.R
import com.user.invoicemanagement.view.fragment.MainFragment


class MainActivity : AppCompatActivity(), MainActivityCallback {

    val TAG = "TAG"

    private var toggle: ActionBarDrawerToggle? = null
    private lateinit var fragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolBar()
        initNavigationView()

        fragmentManager = supportFragmentManager
        val fragment = fragmentManager.findFragmentByTag(TAG)

        if (fragment == null) {
            replaceFragment(MainFragment(), false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }*/


    private fun initNavigationView() {
        // open|close drawer
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle!!)
        toggle!!.syncState()

        // show drawer icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        navigationView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawers()

            /*when (item.itemId) {
                R.id.actionNotification -> showNotificationTab()
                else -> {}
            }*/
            true
        }
    }

    private fun initToolBar() {
        toolbar.setTitle(R.string.app_name)

        setSupportActionBar(toolbar)
    }

    private fun replaceFragment(fragment: Fragment, addBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, TAG)

        if (addBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }


    // Callbacks
    override fun addNewManufacturer() {

    }
}
