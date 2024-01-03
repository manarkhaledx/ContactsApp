package com.example.contactsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter(val items:List<Contact>?): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_contact,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int=items?.size?:0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact=items!![position]
        holder.name.text= contact.name
        holder.phone.text= contact.phone
        holder.image.setImageResource(contact.imageId ?:R.drawable.ic_launcher_background)
        if(onContactClickListener!=null){
            holder.itemView.setOnClickListener{
                onContactClickListener?.onItemClick(position,items!!.get(position))
            }
        }
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name:TextView=itemView.findViewById(R.id.tv_name)
        val phone:TextView=itemView.findViewById(R.id.tv_phone)
        val image: ImageView =itemView.findViewById(R.id.iv_contact)
    }




    var onContactClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int, item: Contact)
    }




}