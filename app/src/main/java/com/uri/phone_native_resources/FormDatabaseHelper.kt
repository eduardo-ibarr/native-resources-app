package com.uri.phone_native_resources

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class FormDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "formdata.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE FormData (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, comment TEXT, imagePath TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS FormData")
        onCreate(db)
    }

    fun insertFormData(name: String, email: String, comment: String, imagePath: String) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("comment", comment)
            put("imagePath", imagePath)
        }
        db.insert("FormData", null, values)
    }

    fun getAllFormData(): List<FormData> {
        val db = readableDatabase
        val cursor = db.query("FormData", null, null, null, null, null, null)
        val formDataList = mutableListOf<FormData>()
        while (cursor.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"))
            val imagePath = cursor.getString(cursor.getColumnIndexOrThrow("imagePath"))
            formDataList.add(FormData(name, email, comment, imagePath))
        }
        cursor.close()
        return formDataList
    }
}

data class FormData(val name: String, val email: String, val comment: String, val imagePath: String)
