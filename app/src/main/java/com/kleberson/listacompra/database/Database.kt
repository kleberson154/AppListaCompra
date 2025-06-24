package com.kleberson.listacompra.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.kleberson.listacompra.exception.ProductExistException
import com.kleberson.listacompra.model.Food

class Database(context: Context): SQLiteOpenHelper(
    context,
    "produtos.db",
    null,
    1
) {
    override fun onCreate(db: android.database.sqlite.SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE IF NOT EXISTS produtos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                preco REAL NOT NULL
            )
            """.trimIndent()

        )
    }

    override fun onUpgrade(db: android.database.sqlite.SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Implement database upgrade logic here
    }

    @SuppressLint("Range")
    fun getProductsAll(): MutableList<Food> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT nome, preco FROM produtos", null)
        val products = mutableListOf<Food>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex("nome"))
                val price = cursor.getDouble(cursor.getColumnIndex("preco"))
                products.add(Food(name, price))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return products
    }

    fun createProduct(name: String, price: Double) {
        val db = writableDatabase
        db.execSQL("INSERT INTO produtos (nome, preco) VALUES (?, ?)", arrayOf(name, price))
        db.close()
    }
}