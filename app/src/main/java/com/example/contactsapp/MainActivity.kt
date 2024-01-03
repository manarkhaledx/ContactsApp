package com.example.contactsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import com.example.contactsapp.databinding.ActivityMainBinding
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    //region initlizations
    private lateinit var etName: EditText
    private lateinit var etPhone: EditText
    private lateinit var etDescription: EditText
    private lateinit var lottieImg: LottieAnimationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var contactList: MutableList<Contact>
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lottieImg = binding.lottie
        initRec()
        addContact()

    }

    private fun initRec() {
        contactList = mutableListOf()
        adapter = ContactAdapter(contactList)
        binding.rvContacts.adapter = adapter
        adapter.onContactClickListener= object :ContactAdapter.OnItemClickListener{
            override fun onItemClick(position: Int, item: Contact) {
                val intent = Intent(this@MainActivity, ContactDetailsActivity::class.java)
                intent.putExtra("contact",item)
                startActivity(intent)
            }
        }
    }


    private fun addContact() {
        binding.apply {
            btnAdd.setOnClickListener {
                val dialogView =
                    LayoutInflater.from(this@MainActivity)
                        .inflate(R.layout.activity_add_contact, null)
                val addContactDialog =
                    AlertDialog.Builder(
                        this@MainActivity,
                        R.style.AlertDialogCustom
                    ) // Use the custom style
                        .setView(dialogView)
                        .create()
                val saveBtn = dialogView.findViewById<Button>(R.id.btn_save)
                val cancelBtn = dialogView.findViewById<Button>(R.id.btn_cancel)

                cancelBtn.setOnClickListener {
                    addContactDialog.dismiss()
                }

                saveBtn.setOnClickListener {
                    val tilName = dialogView.findViewById<TextInputLayout>(R.id.et_name)
                    val tilPhone = dialogView.findViewById<TextInputLayout>(R.id.et_phone)
                    val tilDescription =
                        dialogView.findViewById<TextInputLayout>(R.id.et_description)

                    etName = tilName.editText!!
                    etPhone = tilPhone.editText!!
                    etDescription = tilDescription.editText!!

                    if (!isValidName(etName.text.toString()) || !isValidPhoneNumber(etPhone.text.toString())) {
                        if (!isValidName(etName.text.toString())) {
                            setFieldError(tilName, "Please enter a name correctly (at least 3 characters)")
                        } else {
                            resetFieldError(tilName)
                        }

                        if (!isValidPhoneNumber(etPhone.text.toString())) {
                            setFieldError(tilPhone, "Please enter a valid phone number (at least 11 digits)")
                        } else {
                            resetFieldError(tilPhone)
                        }
                    } else {
                        if (lottieImg.isVisible) {
                            lottie.visibility = View.GONE
                        }
                        contactList.add(
                            Contact(
                                etName.text.toString(),
                                etPhone.text.toString(),
                                etDescription.text.toString(),
                                R.drawable.girl
                            )

                        )
                        Toast.makeText(
                            this@MainActivity,
                            "Contact Added Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        addContactDialog.dismiss()
                    }
                }
                addContactDialog.show()
            }
        }



    }

    //region ValidityFunctions
    private fun isValidName(name: String?): Boolean {
        return !name.isNullOrBlank() && name.length >= 3
    }

    private fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        return !phoneNumber.isNullOrBlank() && phoneNumber.length >= 11 && phoneNumber.isDigitsOnly()
    }

    private fun setFieldError(textInputLayout: TextInputLayout, errorMessage: String) {
        textInputLayout.error = errorMessage
        textInputLayout.boxStrokeColor = ContextCompat.getColor(this@MainActivity, R.color.red)
    }

    private fun resetFieldError(textInputLayout: TextInputLayout) {
        textInputLayout.error = null
        textInputLayout.boxStrokeColor = ContextCompat.getColor(this@MainActivity, R.color.addButtonTextColor)
    }
    //endregion
}
