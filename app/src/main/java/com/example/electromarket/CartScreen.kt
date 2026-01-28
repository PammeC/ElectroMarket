package com.example.electromarket

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    productos: List<Producto>,
    navController: NavController,
    productoViewModel: ProductoViewModel,
    compraViewModel: CompraViewModel
) {
    val cantidades = remember {
        mutableStateMapOf<Producto, Int>().apply {
            productos.forEach { put(it, 1) }
        }
    }

    val subtotal by remember(cantidades) {
        derivedStateOf {
            cantidades.entries.sumOf { (producto, cantidad) ->
                producto.precio * cantidad
            }
        }
    }
    val impuesto = subtotal * 0.15
    val total = subtotal + impuesto

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF6DBE45), titleContentColor = Color.White)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                if (productos.isEmpty()) {
                    Text(
                        "Tu carrito está vacío.",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(productos) { producto ->
                            CartItem(
                                producto = producto,
                                cantidad = cantidades[producto] ?: 1,
                                onAdd = {
                                    cantidades[producto] = (cantidades[producto] ?: 1) + 1
                                },
                                onRemove = {
                                    val current = cantidades[producto] ?: 1
                                    if (current > 1) {
                                        cantidades[producto] = current - 1
                                    }
                                },
                                onDelete = {
                                    cantidades.remove(producto)
                                    productoViewModel.eliminarDelCarrito(producto)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Subtotal: $${"%.2f".format(subtotal)}")
                        Text("IVA (15%): $${"%.2f".format(impuesto)}")
                        Text(
                            "Total: $${"%.2f".format(total)}",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            compraViewModel.setCompraDetalle(
                                productosConCantidad = cantidades.toMap(),
                                subtotal = subtotal,
                                impuesto = impuesto,
                                total = total
                            )
                            navController.navigate("factura")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6DBE45))
                    ) {
                        Text("Confirmar compra", color = Color.White)
                    }

                    OutlinedButton(
                        onClick = { navController.navigate("productos") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        Text("Seguir comprando")
                    }
                }
            }
        }
    )
}

@Composable
fun CartItem(
    producto: Producto,
    cantidad: Int,
    onAdd: () -> Unit,
    onRemove: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = producto.imagenId),
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "$${"%.2f".format(producto.precio)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF3D9A50)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(0.1.dp)
            ) {
                IconButton(onClick = onRemove) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Quitar unidad",
                        tint = Color(0xFFF45B69)
                    )
                }

                Text(
                    text = cantidad.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.width(5.dp),
                    textAlign = TextAlign.Center
                )

                IconButton(onClick = onAdd) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar unidad",
                        tint = Color(0xFF6DBE45)
                    )
                }

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar producto",
                        tint = Color(0xFFF45B69)
                    )
                }
            }
        }
    }
}
