package com.example.thi_gk

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class CustomerAdapter: RecyclerView.Adapter<CustomerAdapter.CustomerViewHoler>() {
    private var stdList: ArrayList<CustomerModal> = ArrayList()
    private var onCLickItem :((CustomerModal) -> Unit)? = null
    private var onCLickDeleteItem :((CustomerModal) -> Unit)? = null

    fun addItem(items:ArrayList<CustomerModal>){
        this.stdList =items
        notifyDataSetChanged()
    }

    fun setOnCLickDeleteItem(callback:(CustomerModal)->Unit){
        this.onCLickDeleteItem=callback
    }



    fun setOnclickItem(callback: (CustomerModal)-> Unit){
        this.onCLickItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CustomerViewHoler(LayoutInflater.from(parent.context).inflate(R.layout.card_item_std,parent,false))

    override fun onBindViewHolder(holder: CustomerViewHoler, position: Int) {
        var std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener(){ onCLickItem?.invoke(std)}
        holder.btnDelete.setOnClickListener(){onCLickDeleteItem?.invoke(std)}
    }

    override fun getItemCount(): Int {
        return stdList.size
    }


    class CustomerViewHoler(var view: View) : RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.id_tv)
        private var name = view.findViewById<TextView>(R.id.name_tv)
        private var age = view.findViewById<TextView>(R.id.age_tv)
        private var phone = view.findViewById<TextView>(R.id.phone_tv2)
        private var email = view.findViewById<TextView>(R.id.email_tv)
        var btnDelete = view.findViewById<Button>(R.id.btnDelete)
        fun bindView(std:CustomerModal){
            id.text = "ID : " + std.id.toString();
            name.text = "Họ tên : "+ std.name;
            age.text = "Tuổi : " + std.age.toString();
            phone.text = "Số điện thoại : " + std.phone.toString();
            email.text = "Email : " + std.email;
        }
    }



}