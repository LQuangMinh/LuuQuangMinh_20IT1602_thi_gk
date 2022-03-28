package com.example.thi_gk

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(
        context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "customer2.db"
        private const val TBL_CUSTOMER = "tbl_customer2"
        private const val ID = "id"
        private const val NAME = "name"
        private const val AGE = "age"
        private const val PHONE = "phone"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblCustomer = ("CREATE TABLE "+ TBL_CUSTOMER+ " (" +ID+ " INTEGER PRIMARY KEY," +NAME+ " TEXT, " +AGE+ " INTEGER," +PHONE+ " INTEGER," +EMAIL+ " TEXT" + ")");
        db?.execSQL(createTblCustomer)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_CUSTOMER")
        onCreate(db)
    }

    fun insertCustomer(std:CustomerModal) : Long  {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(AGE, std.age)
        contentValues.put(PHONE,std.phone)
        contentValues.put(EMAIL,std.email)
        val success = db.insert(TBL_CUSTOMER,null,contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllCustomer(): ArrayList<CustomerModal>{
        val stdList: ArrayList<CustomerModal> = ArrayList();
        val selectQuery = "SELECT * FROM $TBL_CUSTOMER"
        val db = this.readableDatabase
        val cursor: Cursor?
        try {
            cursor =db.rawQuery(selectQuery,null)
        }catch(e: Exception){
            db.execSQL(selectQuery)
            e.printStackTrace()
            return ArrayList()
        }

        var id: Int
        var name: String
        var age: Int
        var phone: Int
        var email: String

        if(cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                age = cursor.getInt(cursor.getColumnIndex("age"))
                phone = cursor.getInt(cursor.getColumnIndex("phone"))
                email = cursor.getString(cursor.getColumnIndex("email"))
                val std =CustomerModal(id =id,name=name,age=age,phone=phone,email=email)
                stdList.add(std)
            }while(cursor.moveToNext())
        }
        return stdList
    }

    fun updateCustomer(std:CustomerModal): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,std.id)
        contentValues.put(NAME,std.name)
        contentValues.put(AGE,std.age)
        contentValues.put(PHONE,std.phone)
        contentValues.put(EMAIL,std.email)
        val success = db.update(TBL_CUSTOMER,contentValues,"id="+std.id,null)
        db.close()
        return success
    }

    fun deleteCustomerById(id: Int) : Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID,id)
        val success = db.delete(TBL_CUSTOMER,"id=$id",null)
        db.close()
        return success
    }

}