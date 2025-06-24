package com.kleberson.listacompra.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.listacompra.R
import com.kleberson.listacompra.controller.MainController
import com.kleberson.listacompra.database.Database
import com.kleberson.listacompra.model.Food
import com.kleberson.listacompra.util.FoodAdapter
import java.util.Locale

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Database(this)
        db.onOpen(db.writableDatabase)
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val listProducts = db.getProductsAll()
        val listRecycle = mutableListOf<Food>()
        for (i in listProducts.indices) {
            if (listProducts[i].name in sharedPreferences.all.values) {
                val food = Food(listProducts[i].name, listProducts[i].price)
                listRecycle.add(food)
            }
        }
        val qtdText = findViewById<TextView>(R.id.textViewQuantidade)
        qtdText.text = String.format(Locale.US, "%d produtos", listRecycle.size)
        val controller = MainController()

        val recyclerView = findViewById<RecyclerView>(R.id.RecycleView)
        val total = findViewById<TextView>(R.id.textViewTotal)
        total.text = String.format(Locale.US,"%.2f", listRecycle.sumOf { it.price })
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FoodAdapter(this, listRecycle){ position ->
            sharedPreferences.edit().remove("product #${position+1}").apply()
            listRecycle.removeAt(position as Int)
            qtdText.text = String.format(Locale.US, "%d produtos", listRecycle.size)

            recyclerView.adapter?.notifyDataSetChanged()
            total.text = String.format(Locale.US,"%.2f", listRecycle.sumOf { it.price })
        }

        val inputName = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val btnAdd = findViewById<Button>(R.id.buttonAdicionar)
        val btnLimpar = findViewById<Button>(R.id.buttonLimpar)
        val btnCreateMain = findViewById<Button>(R.id.buttonCriarMain)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listProducts.map { it.name })
        inputName.setAdapter(adapter)

        btnAdd.setOnClickListener{
            val name = inputName.text.toString().trim()
            if (name.isNotEmpty()) {
                controller.adicionarLista(this, name, listRecycle, sharedPreferences, inputName)
                recyclerView.adapter?.notifyDataSetChanged()
            } else {
                Toast.makeText(this, "Por favor, insira um produto", Toast.LENGTH_SHORT).show()
            }
            qtdText.text = String.format(Locale.US, "%d produtos", listRecycle.size)
            total.text = String.format(Locale.US,"%.2f", listRecycle.sumOf { it.price })
        }

        btnLimpar.setOnClickListener {
            controller.limparLista(this, listRecycle, sharedPreferences, inputName)
            recyclerView.adapter?.notifyDataSetChanged()
            qtdText.text = String.format(Locale.US, "%d produtos", listRecycle.size)
            total.text = String.format(Locale.US,"%.2f", listRecycle.sumOf { it.price })
        }

        btnCreateMain.setOnClickListener {
            Intent(this, NewProductActivity::class.java).apply {
                startActivity(this)
            }
        }

    }
}