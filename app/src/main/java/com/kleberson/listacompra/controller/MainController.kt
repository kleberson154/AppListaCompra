package com.kleberson.listacompra.controller

import android.content.Context
import android.content.SharedPreferences
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.listacompra.database.Database
import com.kleberson.listacompra.model.Food
import java.util.Locale

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

    fun adicionarLista(
        context: Context, name: String, listRecycle: MutableList<Food>,
        sharedPreferences: SharedPreferences?, inputName: AutoCompleteTextView
    ) {
        try {
            val food = addProduct(context, name)
            listRecycle.add(food)
            sharedPreferences?.edit()?.putString("product #${listRecycle.size}", food.name)?.apply()
            inputName.setText("")
            Toast.makeText(context, "Produto adicionado", Toast.LENGTH_SHORT).show()
        } catch (e: IllegalArgumentException) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    fun limparLista(context: Context, listRecycle: MutableList<Food>, sharedPreferences: SharedPreferences?, inputName: AutoCompleteTextView
    ) {
        listRecycle.clear()
        sharedPreferences?.edit()?.clear()?.apply()
        inputName.setText("")
        Toast.makeText(context, "Lista limpa", Toast.LENGTH_SHORT).show()
    }
}