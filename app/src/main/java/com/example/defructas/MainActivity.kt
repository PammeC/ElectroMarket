package com.example.defructas

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.defructas.ui.theme.DeFructasTheme
import com.google.firebase.auth.FirebaseAuth


class MainActivity : ComponentActivity() {

    private val productoViewModel by viewModels<ProductoViewModel>()
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Si no hay usuario autenticado, redirige a LoginActivity
        if (auth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Para que no regrese a esta pantalla sin login
            return
        }

        // Si ya está autenticado, sigue al contenido normal
        setContent {
            DeFructasTheme {
                val navController = rememberNavController()
                AppNavigation(navController, productoViewModel)
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, productoViewModel: ProductoViewModel) {
    val carrito by productoViewModel.carrito.collectAsState()
    val compraViewModel: CompraViewModel = viewModel()

    NavHost(navController = navController, startDestination = "productos") {

        composable("productos") {
            ProductsScreen(
                productoViewModel = productoViewModel,
                navController = navController
            )
        }

        composable("carrito") {
            CartScreen(
                productos = carrito,
                navController = navController,
                productoViewModel = productoViewModel,
                compraViewModel = compraViewModel
            )
        }

        composable("factura") {
            FacturaScreen(
                navController = navController,
                compraViewModel = compraViewModel
            )
        }

        composable("tarjeta") {
            TarjetaScreen(
                navController = navController,
                compraViewModel = compraViewModel
            )
        }

        composable("confirmacion") {
            ConfirmacionScreen(
                navController = navController,
                compraViewModel = compraViewModel,
                productoViewModel = productoViewModel
            )
        }
    }
}



