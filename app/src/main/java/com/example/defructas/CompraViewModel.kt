package com.example.defructas

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class DatosPersonales(
    var nombre: String = "",
    var cedula: String = "",
    var correo: String = "",
    var direccion: String = ""
)

data class CompraDetalle(
    val productosConCantidad: Map<Producto, Int> = emptyMap(),
    val subtotal: Double = 0.0,
    val impuesto: Double = 0.0,
    val total: Double = 0.0,
    val datosPersonales: DatosPersonales = DatosPersonales()
)

class CompraViewModel : ViewModel() {

    private val _compraDetalle = MutableStateFlow(CompraDetalle())
    val compraDetalle: StateFlow<CompraDetalle> = _compraDetalle

    fun setCompraDetalle(
        productosConCantidad: Map<Producto, Int>,
        subtotal: Double,
        impuesto: Double,
        total: Double
    ) {
        _compraDetalle.value = _compraDetalle.value.copy(
            productosConCantidad = productosConCantidad,
            subtotal = subtotal,
            impuesto = impuesto,
            total = total
        )
    }

    fun setDatosPersonales(nombre: String, cedula: String, correo: String, direccion: String) {
        _compraDetalle.value = _compraDetalle.value.copy(
            datosPersonales = DatosPersonales(nombre, cedula, correo, direccion)
        )
    }

    fun limpiarCompra() {
        _compraDetalle.value = CompraDetalle()
    }
}
