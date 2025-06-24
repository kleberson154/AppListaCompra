package com.kleberson.listacompra.controller

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.kleberson.listacompra.database.Database
import com.kleberson.listacompra.exception.ProductExistException
import com.kleberson.listacompra.view.MainActivity

class NewProductController {
    fun createProduct(name: String, price: Double, context: Context) {
        val db = Database(context)
        val listProducts = db.getProductsAll()
        try {
            for (product in listProducts) {
                if (product.name == name) {
                    throw ProductExistException()
                }
            }
            db.createProduct(name, price)
            Toast.makeText(context, "Product created successfully", Toast.LENGTH_SHORT).show()
            context.startActivity(Intent(context, MainActivity::class.java))
        } catch (e: ProductExistException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            return
        }
    }
}