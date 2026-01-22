package com.example.defructas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.text.KeyboardOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TarjetaScreen(navController: NavController, compraViewModel: CompraViewModel) {

    var mostrarDialogoConfirmacion by remember { mutableStateOf(false) }
    var numeroTarjeta by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }

    var errorTarjeta by remember { mutableStateOf(false) }
    var errorFecha by remember { mutableStateOf(false) }
    var errorCVV by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MediumTopAppBar(
                title = { Text("Pago con Tarjeta") },
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
                // Número de tarjeta
                OutlinedTextField(
                    value = numeroTarjeta,
                    onValueChange = {
                        if (it.length <= 16 && it.all { char -> char.isDigit() }) {
                            numeroTarjeta = it
                            errorTarjeta = false
                        }
                    },
                    label = { Text("Número de tarjeta") },
                    isError = errorTarjeta,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorTarjeta) {
                    Text("Número de tarjeta inválido", color = MaterialTheme.colorScheme.error)
                }

                // Fecha de vencimiento
                OutlinedTextField(
                    value = fechaVencimiento,
                    onValueChange = {
                        var digits = it.filter { char -> char.isDigit() }.take(4)
                        if (digits.length >= 3) {
                            digits = digits.substring(0, 2) + "/" + digits.substring(2)
                        }
                        fechaVencimiento = digits
                        errorFecha = false
                    },
                    label = { Text("Fecha de vencimiento (MM/AA)") },
                    isError = errorFecha,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorFecha) {
                    Text("Fecha inválida (usa formato MM/AA)", color = MaterialTheme.colorScheme.error)
                }

                // CVV
                OutlinedTextField(
                    value = cvv,
                    onValueChange = {
                        if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                            cvv = it
                            errorCVV = false
                        }
                    },
                    label = { Text("CVV") },
                    isError = errorCVV,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorCVV) {
                    Text("CVV inválido (3 dígitos)", color = MaterialTheme.colorScheme.error)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val tarjetaValida = numeroTarjeta.length == 16
                        val fechaValida = fechaVencimiento.length == 5 && fechaVencimiento[2] == '/'
                        val cvvValido = cvv.length == 3

                        errorTarjeta = !tarjetaValida
                        errorFecha = !fechaValida
                        errorCVV = !cvvValido

                        if (tarjetaValida && fechaValida && cvvValido) {
                            mostrarDialogoConfirmacion = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Confirmar compra")
                }

                if (mostrarDialogoConfirmacion) {
                    AlertDialog(
                        onDismissRequest = { mostrarDialogoConfirmacion = false },
                        title = { Text("¿Confirmar compra?") },
                        text = { Text("¿Estás seguro de que deseas confirmar esta compra?") },
                        confirmButton = {
                            TextButton(onClick = {
                                mostrarDialogoConfirmacion = false
                                navController.navigate("confirmacion")
                            }) {
                                Text("Sí")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                mostrarDialogoConfirmacion = false
                            }) {
                                Text("No")
                            }
                        }
                    )
                }
            }
        }
    )
}
