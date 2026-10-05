package com.example.parcial1_tiendaderopa

import com.example.parcial1_tiendaderopa.model.Calzado
import com.example.parcial1_tiendaderopa.model.CategoriaRopa
import com.example.parcial1_tiendaderopa.model.Prenda
import com.example.parcial1_tiendaderopa.repository.TiendaRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Pruebas unitarias para verificar el correcto funcionamiento de las clases del Checkpoint 1.
 */
class ExampleUnitTest {

    @Test
    fun testCategoriaRopa() {
        val cat = CategoriaRopa(1, "Ropa Formal", "Prendas elegantes")
        assertEquals(1, cat.getId())
        assertEquals("Ropa Formal", cat.getNombre())
        assertEquals("Prendas elegantes", cat.getDescripcion())
    }

    @Test
    fun testPrenda() {
        val cat = CategoriaRopa(1, "Ropa Formal", "Prendas elegantes")
        val prenda = Prenda(1, "Camisa", 100.0, 5, cat, "M", "Azul", "Masculino")

        assertEquals(1, prenda.getId())
        assertEquals("Camisa", prenda.getNombre())
        assertEquals(100.0, prenda.getPrecioBase(), 0.01)
        assertEquals(5, prenda.getStock())
        assertEquals(cat, prenda.getCategoria())
        assertEquals("M", prenda.getTalla())
        assertEquals("Azul", prenda.getColor())
        assertEquals("Masculino", prenda.getGenero())
        assertEquals(100.0, prenda.calcularPrecioFinal(), 0.01)
        assertNotNull(prenda.obtenerDetalle())
    }

    @Test
    fun testCalzado() {
        val cat = CategoriaRopa(2, "Calzado Urbano", "Zapatillas cotidianas")
        val calzado = Calzado(2, "Sneakers", 200.0, 10, cat, 42, "Goma")

        assertEquals(2, calzado.getId())
        assertEquals("Sneakers", calzado.getNombre())
        assertEquals(200.0, calzado.getPrecioBase(), 0.01)
        assertEquals(10, calzado.getStock())
        assertEquals(cat, calzado.getCategoria())
        assertEquals(42, calzado.getNumeroTalle())
        assertEquals("Goma", calzado.getTipoSuela())
        assertEquals(200.0, calzado.calcularPrecioFinal(), 0.01)
        assertNotNull(calzado.obtenerDetalle())
    }

    @Test
    fun testTiendaRepository() {
        val repo = TiendaRepository()
        assertEquals(3, repo.obtenerCategorias().size)
        assertEquals(4, repo.obtenerProductos().size)

        val producto = repo.buscarProductoPorId(1)
        assertNotNull(producto)
        assertEquals("Camisa Formal", producto?.getNombre())
    }
}