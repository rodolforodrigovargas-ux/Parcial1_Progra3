package com.example.parcial1_tiendaderopa.model

class CategoriaRopa(
    val id: Int,
    val nombre: String,
    val descripcion: String
) {
    @JvmName("getIdFunc")
    fun getId(): Int = id

    @JvmName("getNombreFunc")
    fun getNombre(): String = nombre

    @JvmName("getDescripcionFunc")
    fun getDescripcion(): String = descripcion
}
