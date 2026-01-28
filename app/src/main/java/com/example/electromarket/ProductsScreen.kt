package com.example.electromarket

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color


data class Producto(
    val nombre: String,
    val precio: Double,
    val imagenId: Int,
    val region: String // Cosina, Hogar, Tecnología
)

val verdeLima = Color(0xFF6DBE45)
val rojoFresa = Color(0xFFF45B69)
val amarilloMango = Color(0xFFFFD447)
val naranjaBrillante = Color(0xFFF99E1A)
val marronContorno = Color(0xFF5C3A1E)
val verdeMango = Color(0xFF3D9A50)
val verdePalido = Color(0xFFC9EAA6)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductsScreen(productoViewModel: ProductoViewModel, navController: NavController) {

    val productos by productoViewModel.productos.collectAsState()
    val carrito by productoViewModel.carrito.collectAsState()
    val cantidadCarrito = carrito.size
    var regionSeleccionada by remember { mutableStateOf("Cocina") }
    val productosFiltrados = productos.filter { it.region == regionSeleccionada }

    val snackbarHostState = remember { SnackbarHostState() }
    var productoAñadido by remember { mutableStateOf<Producto?>(null) }

    LaunchedEffect(productoAñadido) {
        productoAñadido?.let {
            snackbarHostState.showSnackbar("${it.nombre} añadido al carrito")
            productoAñadido = null
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(end = 8.dp)
                        )
                        Text("ElectroMarket", color = marronContorno)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("carrito") }) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge(
                                        containerColor = rojoFresa,
                                        contentColor = Color.White
                                    ) {
                                        Text(cantidadCarrito.toString())
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrito",
                                tint = verdeMango,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        val context = navController.context
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        (context as? ComponentActivity)?.finish()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = rojoFresa,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verdePalido)
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            RegionSelector(regionActual = regionSeleccionada) { nuevaRegion ->
                regionSeleccionada = nuevaRegion
            }

            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(productosFiltrados) { producto ->
                    ProductCard(
                        producto = producto,
                        onAddToCart = {
                            productoViewModel.agregarAlCarrito(producto)
                            productoAñadido = producto
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RegionSelector(regionActual: String, onRegionChange: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        listOf("Cocina", "Hogar", "Tecnología").forEach { region ->
            Button(
                onClick = { onRegionChange(region) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (region == regionActual) verdeLima else verdePalido,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(region)
            }
        }
    }
}

@Composable
fun ProductCard(producto: Producto, onAddToCart: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = producto.imagenId),
                contentDescription = producto.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = marronContorno
                )
            )

            Text(
                text = "Precio: $${producto.precio} ",
                style = MaterialTheme.typography.bodyMedium.copy(color = verdeMango)
            )

            Button(
                onClick = onAddToCart,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = naranjaBrillante)
            ) {
                Text("Agregar al carrito", color = Color.White)
            }
        }
    }
}

