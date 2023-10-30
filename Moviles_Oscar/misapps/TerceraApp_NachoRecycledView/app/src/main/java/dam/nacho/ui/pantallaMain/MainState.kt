package dam.nacho.ui.pantallaMain

import dam.nacho.domain.modelo.Raton

data class MainState(
    val raton: Raton = Raton("null","null","null",0,0,0, "0/0/0"),
    val tipoLlamada: String? =null,
    val error: String? = null
)