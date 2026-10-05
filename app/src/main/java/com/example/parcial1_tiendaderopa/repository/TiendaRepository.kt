package com.example.parcial1_tiendaderopa.repository

import com.example.parcial1_tiendaderopa.model.Calzado
import com.example.parcial1_tiendaderopa.model.CategoriaRopa
import com.example.parcial1_tiendaderopa.model.Prenda
import com.example.parcial1_tiendaderopa.model.Producto

class TiendaRepository(
    private val listaCategorias: List<CategoriaRopa> = listOf(
        CategoriaRopa(1, "Ropa Formal", "Prendas y vestir para eventos formales"),
        CategoriaRopa(2, "Ropa Deportiva", "Indumentaria cómoda para deportes y ejercicios"),
        CategoriaRopa(3, "Calzado Urbano", "Zapatillas y zapatos de uso diario")
    ),
    private val listaProductos: List<Producto> = listOf(
        Prenda(1, "Camisa Formal", 150.0, 10, CategoriaRopa(1, "Ropa Formal", "Prendas y vestir para eventos formales"), "M", "Blanco", "Masculino"),
        Prenda(2, "Polera Deportiva", 80.0, 25, CategoriaRopa(2, "Ropa Deportiva", "Indumentaria cómoda para deportes y ejercicios"), "L", "Negro", "Unisex"),
        Calzado(3, "Zapatillas Running", 250.0, 15, CategoriaRopa(3, "Calzado Urbano", "Zapatillas y zapatos de uso diario"), 42, "Goma Antideslizante"),
        Calzado(4, "Zapatos de Cuero", 300.0, 8, CategoriaRopa(1, "Ropa Formal", "Prendas y vestir para eventos formales"), 41, "Cuero Sintético")
    )
) {
    fun obtenerCategorias(): List<CategoriaRopa> = listaCategorias

    fun obtenerProductos(): List<Producto> = listaProductos

    fun buscarProductoPorId(id: Int): Producto? = listaProductos.find { it.id == id }
}
