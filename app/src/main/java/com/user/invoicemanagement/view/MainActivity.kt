package com.user.invoicemanagement.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import com.user.invoicemanagement.R
import com.user.invoicemanagement.view.fragment.ArchiveFragment
import com.user.invoicemanagement.view.fragment.MainFragment
import com.user.invoicemanagement.view.fragment.MainTabLayoutFragment
import com.user.invoicemanagement.view.fragment.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val TAG = "TAG"
    private var toggle: ActionBarDrawerToggle? = null
    private var fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolBar()
        initNavigationView()
        initFilter()

        val fragment = fragmentManager.findFragmentByTag(TAG)

        if (fragment == null) {
            replaceFragment(MainTabLayoutFragment(), false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle!!.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }



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

            when (it.itemId) {
                R.id.action_archive -> {
                    replaceFragment(ArchiveFragment(), true)
                }
                R.id.action_settings -> {
                    replaceFragment(SettingsFragment(), true)
                }
                R.id.action_home -> {
                    replaceFragment(MainTabLayoutFragment(), true)
                }
                R.id.action_all_list -> {
                    replaceFragment(MainFragment(), true)
                }
                else -> {}
            }
            true
        }
    }

    private fun initToolBar() {
//        toolbar.setTitle(R.string.app_name)

        setSupportActionBar(toolbar)
    }

    private fun initFilter() {
        tvFilter.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    if (tvFilter.text.isEmpty()) {
                        btnClear.visibility = View.GONE
                    } else {
                        btnClear.visibility = View.VISIBLE
                    }
        })
        btnClear.setOnClickListener {
            tvFilter.setText("")
        }
    }

    private fun replaceFragment(fragment: Fragment, addBackStack: Boolean) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment, TAG)

        if (addBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
