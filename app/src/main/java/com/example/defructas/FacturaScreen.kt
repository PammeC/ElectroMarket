package com.example.defructas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FacturaScreen(navController: NavController, compraViewModel: CompraViewModel) {
    var nombre by remember { mutableStateOf("") }
    var cedula by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf(false) }
    var errorCedula by remember { mutableStateOf(false) }
    var errorCorreo by remember { mutableStateOf(false) }
    var errorDireccion by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Datos de Facturación") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = {
                        nombre = it
                        errorNombre = false
                    },
                    label = { Text("Nombre completo") },
                    singleLine = true,
                    isError = errorNombre,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorNombre) {
                    Text("Ingresa tu nombre completo", color = MaterialTheme.colorScheme.error)
                }

                OutlinedTextField(
                    value = cedula,
                    onValueChange = {
                        if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                            cedula = it
                            errorCedula = it.length == 10 && !ValidadorDatos.esCedulaEcuatorianaValida(it)
                        }
                    },
                    label = { Text("Cédula") },
                    singleLine = true,
                    isError = errorCedula,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorCedula) {
                    Text("Cédula ecuatoriana inválida", color = MaterialTheme.colorScheme.error)
                }

                OutlinedTextField(
                    value = correo,
                    onValueChange = {
                        correo = it
                        errorCorreo = false
                    },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    isError = errorCorreo,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorCorreo) {
                    Text("Correo electrónico no válido", color = MaterialTheme.colorScheme.error)
                }

                OutlinedTextField(
                    value = direccion,
                    onValueChange = {
                        direccion = it
                        errorDireccion = false
                    },
                    label = { Text("Dirección de entrega") },
                    singleLine = false,
                    isError = errorDireccion,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                )
                if (errorDireccion) {
                    Text("Ingresa una dirección válida", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        errorNombre = nombre.isBlank()
                        errorCedula = !ValidadorDatos.esCedulaEcuatorianaValida(cedula)
                        errorCorreo = !ValidadorDatos.esCorreoValido(correo)
                        errorDireccion = direccion.isBlank()

                        if (!errorNombre && !errorCedula && !errorCorreo && !errorDireccion) {
                            compraViewModel.setDatosPersonales(
                                nombre = nombre,
                                cedula = cedula,
                                correo = correo,
                                direccion = direccion
                            )
                            navController.navigate("tarjeta")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continuar con el pago")
                }

                Text(
                    text = "⚠️ Para seguir comprando, se necesitará una tarjeta de débito o crédito.",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    )
}
