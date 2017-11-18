package com.user.invoicemanagement.view.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.user.invoicemanagement.R
import com.user.invoicemanagement.other.Constant
import com.user.invoicemanagement.presenter.SettingsPresenter


class SettingsFragment : BaseFragment() {

    private lateinit var edtEmail: EditText
    private lateinit var presenter: SettingsPresenter
    private val uploadDbCode = 111
    private val getDbCode = 222
    var ACCESS_EXTERNAL_STORAGE_STATE = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val filterView = activity.findViewById<AutoCompleteTextView>(R.id.tvFilter)
        filterView.visibility = View.GONE

        presenter = SettingsPresenter(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val saveButton = view.findViewById<Button>(R.id.btnSettingsSave)
        val getDbButton = view.findViewById<Button>(R.id.btnSettingsGetDb)
        val uploadDbButton = view.findViewById<Button>(R.id.btnSettingsUploadDb)
        edtEmail = view.findViewById(R.id.edtEmailForReports)

        // get settings
        val preferences = activity.getSharedPreferences(Constant.settingsName, Context.MODE_PRIVATE)
        edtEmail.setText(preferences.getString(Constant.emailForReportsKey, ""))

        saveButton.setOnClickListener { save() }
        getDbButton.setOnClickListener { getDb() }
        uploadDbButton.setOnClickListener { uploadDb() }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != RESULT_OK || data == null) {
            return
        }
        when (requestCode) {
            uploadDbCode -> {
                val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (permission !== PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ACCESS_EXTERNAL_STORAGE_STATE = 1
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), ACCESS_EXTERNAL_STORAGE_STATE)
                }
                presenter.replaceDb(data.data.path, context)
            }
            getDbCode -> {
                val permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                if (permission !== PackageManager.PERMISSION_GRANTED) {
                    // We don't have permission so prompt the user
                    ACCESS_EXTERNAL_STORAGE_STATE = 1
                    ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), ACCESS_EXTERNAL_STORAGE_STATE)
                }
                presenter.moveDbFile(data.data.path, activity)
                return
            }
            else -> return
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            ACCESS_EXTERNAL_STORAGE_STATE -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Write to the storage (ex: call appendByteBuffer(byte[] data) here)
                } else {
                    Toast.makeText(context, "Please grant permission.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch(requestCode) {
            case ACCESS_EXTERNAL_STORAGE_STATE : {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Write to the storage (ex: call appendByteBuffer(byte[] data) here)

                } else {
                    Toast.makeText(context, "Please grant permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }*/


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

    private fun getDb() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            startActivityForResult(Intent.createChooser(intent, "Choose directory"), getDbCode)
        } else {
            showToast("Your Android version is too old for this action")
        }
    }

    private fun uploadDb() {
        val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Select a file"), uploadDbCode)
    }

    override fun filter(name: String) {

    }

    override fun getAll() {

    }

}
