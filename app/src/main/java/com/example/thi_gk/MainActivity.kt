package com.example.thi_gk

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.card_item_std.*

class MainActivity : AppCompatActivity() {

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: CustomerAdapter?= null
    private var std:CustomerModal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView =findViewById(R.id.recylerView)
        fun initRecyclerView(){
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = CustomerAdapter();
            recyclerView.adapter =adapter
        }
        initRecyclerView()

        fun getCustomer(){
            val stdList = sqliteHelper.getAllCustomer()
            adapter?.addItem(stdList);
        }

        sqliteHelper = SQLiteHelper(this)

        fun updateCustomer(){
            val name =name_textview.text.toString()
            val age = age_textview.text.toString().toInt()
            val phone = phone_textview.text.toString().toInt()
            val email = email_textview.text.toString()
            if(name == std?.name && age == std?.age  && phone == std?.phone  && email == std?.email ){
                Toast.makeText(this,"Thông tin chưa được lưu..",Toast.LENGTH_SHORT).show()
                return
            }
            if(std==null) return
            val std = CustomerModal(id=std!!.id,name=name,age=age)
            val status = sqliteHelper.updateCustomer(std)
            if(status > -1){
                name_textview.setText("")
                age_textview.setText("")
                phone_textview.setText("")
                email_textview.setText("")
                getCustomer()
                Toast.makeText(this,"Cập nhật thành công !",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Cập nhật thất bại !",Toast.LENGTH_SHORT).show()
            }

        }


        fun addCustomer(){
            val name = name_textview.text.toString()
            val age = age_textview.text.toString();
            val phone = phone_textview.text.toString();
            val email = email_textview.text.toString();
            if(name.isEmpty() || age.isEmpty() || phone.isEmpty() || email.isEmpty()){
                Toast.makeText(this,"Vui lòng nhập thông tin !",Toast.LENGTH_SHORT).show()
            }else{
                val std =CustomerModal(name=name,age=age.toInt(),phone=phone.toInt(),email=email)
                val status = sqliteHelper.insertCustomer(std);
                if(status > -1){
                    Toast.makeText(this,"Đã thêm khách hàng !",Toast.LENGTH_SHORT).show()
                    name_textview.setText("")
                    age_textview.setText("")
                    phone_textview.setText("")
                    email_textview.setText("")
                    name_textview.requestFocus()
                    getCustomer()
                }else{
                    Toast.makeText(this,"Lỗi !",Toast.LENGTH_SHORT).show()
                }
            }
        }

        fun deleteCustomer(id:Int){
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Bạn chắc chắn muốn xóa khách hàng naỳ ?")
            builder.setCancelable(true)
            builder.setPositiveButton("Có"){ dialog,_ ->
                sqliteHelper.deleteCustomerById(id)
                getCustomer()
                Toast.makeText(this,"Đã xóa !",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("Không"){ dialog,_ ->
                dialog.dismiss()
            }
            val alert =builder.create()
            alert.show()
        }


        btnAdd.setOnClickListener{addCustomer()}

        adapter?.setOnclickItem {
            name_textview.setText(it.name)
            age_textview.setText((it.age).toString())
            phone_textview.setText((it.phone).toString())
            email_textview.setText((it.email))
            std = it
        }

        adapter?.setOnCLickDeleteItem {
            deleteCustomer(it.id)
        }

        btnUpdate.setOnClickListener(){updateCustomer()}
    }
}