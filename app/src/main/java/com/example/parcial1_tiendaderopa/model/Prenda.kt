package com.example.parcial1_tiendaderopa.model

class Prenda(
    id: Int,
    nombre: String,
    precioBase: Double,
    stock: Int,
    categoria: CategoriaRopa,
    val talla: String,
    val color: String,
    val genero: String
) : Producto(id, nombre, precioBase, stock, categoria) {

    @JvmName("getTallaFunc")
    fun getTalla(): String = talla

    @JvmName("getColorFunc")
    fun getColor(): String = color

    @JvmName("getGeneroFunc")
    fun getGenero(): String = genero

    override fun calcularPrecioFinal(): Double {
        return precioBase
    }

    override fun obtenerDetalle(): String {
        return "Prenda: $nombre | Talla: $talla | Color: $color | Género: $genero | Categoría: ${categoria.nombre} | Precio Final: $$precioBase"
    }
}
