package com.kleberson.listacompra.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kleberson.listacompra.R
import com.kleberson.listacompra.model.Food

class FoodAdapter(val context: Context, val foods: List<Food>,val onLongClickListener: (Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(foods[position])

        holder.itemView.setOnLongClickListener {
            onLongClickListener(position)
            true
        }
    }
}