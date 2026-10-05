package com.example.parcial1_tiendaderopa.model

abstract class Producto(
    val id: Int,
    val nombre: String,
    val precioBase: Double,
    val stock: Int,
    val categoria: CategoriaRopa
) {
    @JvmName("getIdFunc")
    fun getId(): Int = id

    @JvmName("getNombreFunc")
    fun getNombre(): String = nombre

    @JvmName("getPrecioBaseFunc")
    fun getPrecioBase(): Double = precioBase

    @JvmName("getStockFunc")
    fun getStock(): Int = stock

    @JvmName("getCategoriaFunc")
    fun getCategoria(): CategoriaRopa = categoria

    abstract fun calcularPrecioFinal(): Double
    abstract fun obtenerDetalle(): String
}
