package com.kleberson.listacompra.model

class Food(
    val name: String,
    val price: Double
) {
    override fun toString(): String {
        return "$name - R$ $price"
    }
}