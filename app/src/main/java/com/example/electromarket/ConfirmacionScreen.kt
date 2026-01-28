package com.example.electromarket

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmacionScreen(
    navController: NavController,
    compraViewModel: CompraViewModel,
    productoViewModel: ProductoViewModel
) {
    val compraDetalle by compraViewModel.compraDetalle.collectAsState()
    val verdeLima = Color(0xFF6DBE45)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Compra Exitosa", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = verdeLima)
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "¡Gracias por tu compra!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = verdeLima
                )

                // Detalle de la factura
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🧾 Detalle de la compra", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))

                        compraDetalle.productosConCantidad.forEach { (producto, cantidad) ->
                            Text("- ${producto.nombre} x$cantidad = $${"%.2f".format(producto.precio * cantidad)}")
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Divider()

                        Text("Subtotal: $${"%.2f".format(compraDetalle.subtotal)}")
                        Text("IVA (15%): $${"%.2f".format(compraDetalle.impuesto)}")
                        Text(
                            "Total: $${"%.2f".format(compraDetalle.total)}",
                            style = MaterialTheme.typography.titleMedium,
                            color = verdeLima
                        )
                    }
                }

                // Datos del cliente
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📌 Datos del cliente", style = MaterialTheme.typography.titleMedium)

                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Nombre: ${compraDetalle.datosPersonales.nombre}")
                        Text("Cédula: ${compraDetalle.datosPersonales.cedula}")
                        Text("Correo: ${compraDetalle.datosPersonales.correo}")
                        Text("Dirección: ${compraDetalle.datosPersonales.direccion}")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        productoViewModel.vaciarCarrito()
                        compraViewModel.limpiarCompra()
                        navController.navigate("productos") {
                            popUpTo("productos") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = verdeLima),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver a Productos", color = Color.White)
                }
            }
        }
    )
}

