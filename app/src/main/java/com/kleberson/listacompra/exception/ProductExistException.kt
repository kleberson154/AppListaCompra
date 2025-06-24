package com.kleberson.listacompra.exception

class ProductExistException(message: String = "Product already exists") : Exception(message) {
    override val message: String?
        get() = "Erro: ${super.message}"
}