package com.example.defructas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductoViewModel : ViewModel() {

    // Lista de productos
    private val _productos = MutableStateFlow(
        listOf(
            // 🍳 Cocina
            Producto("Refrigeradora LG", 850.00, R.drawable.refrigeradora, "Cocina"),
            Producto("Cocina a Gas Indurama", 620.00, R.drawable.cocina_gas, "Cocina"),
            Producto("Microondas Samsung", 180.00, R.drawable.microondas, "Cocina"),
            Producto("Licuadora Oster", 95.00, R.drawable.licuadora, "Cocina"),
            Producto("Horno Eléctrico", 210.00, R.drawable.horno, "Cocina"),

            // 🏠 Hogar
            Producto("Lavadora Whirlpool", 780.00, R.drawable.lavadora, "Hogar"),
            Producto("Secadora LG", 720.00, R.drawable.secadora, "Hogar"),
            Producto("Aspiradora Xiaomi", 250.00, R.drawable.aspiradora, "Hogar"),
            Producto("Plancha Philips", 65.00, R.drawable.plancha, "Hogar"),
            Producto("Ventilador Industrial", 130.00, R.drawable.ventilador, "Hogar"),

            // 💻 Tecnología
            Producto("Smart TV 55\" Samsung", 980.00, R.drawable.smart_tv, "Tecnología"),
            Producto("Laptop HP Pavilion", 1150.00, R.drawable.laptop, "Tecnología"),
            Producto("Tablet Lenovo", 320.00, R.drawable.tablet, "Tecnología"),
            Producto("Equipo de Sonido Sony", 410.00, R.drawable.sonido, "Tecnología"),
            Producto("Router TP-Link", 85.00, R.drawable.router, "Tecnología")
        )

    )
    val productos: StateFlow<List<Producto>> = _productos.asStateFlow()

    // Lista de productos agregados al carrito
    private val _carrito = MutableStateFlow<List<Producto>>(emptyList())
    val carrito: StateFlow<List<Producto>> = _carrito.asStateFlow()

    // Función para agregar producto al carrito
    fun agregarAlCarrito(producto: Producto) {
        _carrito.value = _carrito.value + producto
    }

    // Opcional: para eliminar un producto del carrito
    fun eliminarDelCarrito(producto: Producto) {
        _carrito.value = _carrito.value - producto
    }

    // Opcional: para vaciar el carrito
    fun vaciarCarrito() {
        _carrito.value = emptyList()
    }

}
