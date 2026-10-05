package com.example.parcial1_tiendaderopa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import com.example.parcial1_tiendaderopa.ui.screens.CategoriasScreen
import com.example.parcial1_tiendaderopa.ui.screens.ProductosScreen
import com.example.parcial1_tiendaderopa.ui.theme.Parcial1_TiendaDeRopaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Parcial1_TiendaDeRopaTheme {
                Parcial1_TiendaDeRopaApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Parcial1_TiendaDeRopaApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.PRODUCTOS) }
    var categoriaSeleccionadaId by remember { mutableStateOf<Int?>(null) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach { destination ->
                item(
                    icon = {
                        Icon(
                            painterResource(destination.icon),
                            contentDescription = destination.label
                        )
                    },
                    label = { Text(destination.label) },
                    selected = destination == currentDestination,
                    onClick = {
                        if (destination == AppDestinations.PRODUCTOS) {
                            categoriaSeleccionadaId = null
                        }
                        currentDestination = destination
                    }
                )
            }
        }
    ) {
        when (currentDestination) {
            AppDestinations.PRODUCTOS -> {
                ProductosScreen(
                    categoriaInicialId = categoriaSeleccionadaId
                )
            }
            AppDestinations.CATEGORIAS -> {
                CategoriasScreen(
                    onCategoriaClick = { catId ->
                        categoriaSeleccionadaId = catId
                        currentDestination = AppDestinations.PRODUCTOS
                    }
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    PRODUCTOS("Productos", R.drawable.ic_home),
    CATEGORIAS("Categorías", R.drawable.ic_favorite),
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    Parcial1_TiendaDeRopaTheme {
        Parcial1_TiendaDeRopaApp()
    }
}
