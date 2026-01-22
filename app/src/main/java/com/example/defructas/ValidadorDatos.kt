package com.example.defructas

object ValidadorDatos {

    fun esCedulaEcuatorianaValida(cedula: String): Boolean {
        if (cedula.length != 10) return false
        val digitos = cedula.map { it.toString().toIntOrNull() ?: return false }
        val provincia = cedula.substring(0, 2).toInt()
        if (provincia !in 1..24) return false

        val digitoVerificador = digitos[9]
        val suma = digitos.take(9).mapIndexed { i, d ->
            if (i % 2 == 0) {
                val mult = d * 2
                if (mult > 9) mult - 9 else mult
            } else d
        }.sum()

        val decenaSuperior = ((suma + 9) / 10) * 10
        val verificadorCalculado = if (decenaSuperior - suma == 10) 0 else decenaSuperior - suma

        return verificadorCalculado == digitoVerificador
    }

    fun esCorreoValido(correo: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(correo).matches()
    }
}