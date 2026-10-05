package com.example.parcial1_tiendaderopa.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parcial1_tiendaderopa.model.Calzado
import com.example.parcial1_tiendaderopa.model.Prenda
import com.example.parcial1_tiendaderopa.model.Producto
import com.example.parcial1_tiendaderopa.repository.TiendaRepository
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductosScreen(
    repository: TiendaRepository = remember { TiendaRepository() },
    categoriaInicialId: Int? = null
) {
    var categoriaSeleccionadaId by remember { mutableStateOf(categoriaInicialId) }
    var tipoFiltro by remember { mutableStateOf("TODOS") }

    val categorias = repository.obtenerCategorias()
    val productos = repository.obtenerProductos().filter { producto ->
        val cumpleCategoria = categoriaSeleccionadaId == null || producto.categoria.id == categoriaSeleccionadaId
        val cumpleTipo = when (tipoFiltro) {
            "PRENDA" -> producto is Prenda
            "CALZADO" -> producto is Calzado
            else -> true
        }
        cumpleCategoria && cumpleTipo
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo de Productos", fontWeight = FontWeight.Bold) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            // Filtros por Tipo (Todos, Prenda, Calzado)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = tipoFiltro == "TODOS",
                    onClick = { tipoFiltro = "TODOS" },
                    label = { Text("Todos") }
                )
                FilterChip(
                    selected = tipoFiltro == "PRENDA",
                    onClick = { tipoFiltro = "PRENDA" },
                    label = { Text("Prendas") }
                )
                FilterChip(
                    selected = tipoFiltro == "CALZADO",
                    onClick = { tipoFiltro = "CALZADO" },
                    label = { Text("Calzados") }
                )
            }

            // Filtros por Categoría
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                item {
                    FilterChip(
                        selected = categoriaSeleccionadaId == null,
                        onClick = { categoriaSeleccionadaId = null },
                        label = { Text("Todas las Categorías") }
                    )
                }
                items(categorias) { cat ->
                    FilterChip(
                        selected = categoriaSeleccionadaId == cat.id,
                        onClick = { categoriaSeleccionadaId = cat.id },
                        label = { Text(cat.nombre) }
                    )
                }
            }

            // Contador de resultados
            Text(
                text = "${productos.size} productos encontrados",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Lista de Productos
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(productos, key = { it.id }) { producto ->
                    ProductoItemCard(producto = producto)
                }
            }
        }
    }
}

@Composable
fun ProductoItemCard(producto: Producto) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Encabezado: Nombre y Tipo de Producto
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                val (badgeText, badgeBg) = when (producto) {
                    is Prenda -> "Prenda" to MaterialTheme.colorScheme.primaryContainer
                    is Calzado -> "Calzado" to MaterialTheme.colorScheme.tertiaryContainer
                    else -> "Producto" to MaterialTheme.colorScheme.secondaryContainer
                }

                Surface(
                    color = badgeBg,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(
                        text = badgeText,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Categoría y Precio Base / Final
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SuggestionChip(
                    onClick = { },
                    label = { Text(producto.categoria.nombre, style = MaterialTheme.typography.labelMedium) }
                )

                Text(
                    text = "$${String.format(Locale.US, "%.2f", producto.calcularPrecioFinal())}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Detalles Polimórficos según el tipo de objeto
            when (producto) {
                is Prenda -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Talla: ${producto.talla}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Color: ${producto.color}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Género: ${producto.genero}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
                is Calzado -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Talle N°: ${producto.numeroTalle}", style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Suela: ${producto.tipoSuela}", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stock disponible: ${producto.stock} unids.",
                    style = MaterialTheme.typography.bodySmall,
                    color = if (producto.stock > 5) MaterialTheme.colorScheme.outline else MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Detalle Formateado generado por obtenerDetalle()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .padding(8.dp)
            ) {
                Text(
                    text = producto.obtenerDetalle(),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
