package com.example.parcial1_tiendaderopa.model

class Calzado(
    id: Int,
    nombre: String,
    precioBase: Double,
    stock: Int,
    categoria: CategoriaRopa,
    val numeroTalle: Int,
    val tipoSuela: String
) : Producto(id, nombre, precioBase, stock, categoria) {

    @JvmName("getNumeroTalleFunc")
    fun getNumeroTalle(): Int = numeroTalle

    @JvmName("getTipoSuelaFunc")
    fun getTipoSuela(): String = tipoSuela

    override fun calcularPrecioFinal(): Double {
        return precioBase
    }

    override fun obtenerDetalle(): String {
        return "Calzado: $nombre | Talle: $numeroTalle | Suela: $tipoSuela | Categoría: ${categoria.nombre} | Precio Final: $$precioBase"
    }
}
