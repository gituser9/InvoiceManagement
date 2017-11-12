package com.user.invoicemanagement.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import com.user.invoicemanagement.R
import com.user.invoicemanagement.other.Constant


class SettingsFragment : BaseFragment() {

    private lateinit var edtEmail: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filterView = activity.findViewById<AutoCompleteTextView>(R.id.tvFilter)
        filterView.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val saveButton = view.findViewById<Button>(R.id.btnSettingsSave)
        edtEmail = view.findViewById(R.id.edtEmailForReports)

        // get settings
        val preferences = activity.getSharedPreferences(Constant.settingsName, Context.MODE_PRIVATE)
        edtEmail.setText(preferences.getString(Constant.emailForReportsKey, ""))

        saveButton.setOnClickListener { save() }

        return view
    }

    private fun save() {
        if (edtEmail.text.isEmpty()) {
            showAlert(R.string.email_is_required)
            return
        }
        val preferences = activity.getSharedPreferences(Constant.settingsName, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString(Constant.emailForReportsKey, edtEmail.text.toString())
        editor.apply()
    }

    override fun filter(name: String) {

    }

    override fun getAll() {

    }

}
