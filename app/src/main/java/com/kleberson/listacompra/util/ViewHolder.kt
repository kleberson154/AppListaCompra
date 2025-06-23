package com.kleberson.listacompra.util

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.listacompra.R
import com.kleberson.listacompra.model.Food
import java.util.Locale

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(food: Food) {
        itemView.findViewById<TextView>(R.id.nomeTextView).text = food.name
        itemView.findViewById<TextView>(R.id.precoTextView).text = String.format(Locale.US, "%.2f", food.price)
    }

}