package com.example.contactsapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.contactsapp.databinding.ActivityContactDetailsBinding

class ContactDetailsActivity : AppCompatActivity() {
    lateinit var backbtn:Button
    lateinit var nameTV:TextView
    lateinit var phoneTv:TextView
    lateinit var decriptionTv:TextView
    lateinit var image:ImageView
    lateinit var binding: ActivityContactDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityContactDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val contact: Contact? = intent.getParcelableExtra("contact")
        binding.apply {
            backbtn=binding.btnBack
            nameTV=binding.tvNamedetail
            phoneTv=binding.tvPhonedetail
            image=binding.ivDetail
            decriptionTv=binding.tvDescriptiondetail
            backbtn.setOnClickListener{
                finish()
            }
            nameTV.setText(contact?.name.toString())
            phoneTv.setText(contact?.phone.toString())
            decriptionTv.setText(contact?.description.toString())
            contact?.imageId?.let { image.setImageResource(it) }
        }
    }
}