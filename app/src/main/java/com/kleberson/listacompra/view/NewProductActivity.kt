package com.kleberson.listacompra.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.kleberson.listacompra.R
import com.kleberson.listacompra.controller.NewProductController

class NewProductActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.newproduct_activity)

        val nameProduct = findViewById<EditText>(R.id.editTextCreateName)
        val priceProduct = findViewById<EditText>(R.id.editTextCreatePrice)
        val buttonCreate = findViewById<Button>(R.id.buttonCreateItem)

        buttonCreate.setOnClickListener{
            val name = nameProduct.text.toString()
            val price = priceProduct.text.toString().toDoubleOrNull()

            if (name.isNotEmpty() && price != null) {
                val controller = NewProductController()
                controller.createProduct(name, price, this)
            } else {
                nameProduct.error = "Please enter a valid name"
                priceProduct.error = "Please enter a valid price"
            }
        }
    }
}