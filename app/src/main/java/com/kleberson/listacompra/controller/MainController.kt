package com.kleberson.listacompra.controller

import android.content.Context
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.listacompra.database.Database
import com.kleberson.listacompra.model.Food

class MainController {
    fun addProduct(context: Context, name: String): Food {
        val db = Database(context)
        val listProducts = db.getProductsAll()

        val foundProduct = listProducts.find { it.name == name }
        return if (foundProduct != null) {
            Food(name, foundProduct.price)
        } else {
            throw IllegalArgumentException("Produto não encontrado: $name")
        }
    }
}