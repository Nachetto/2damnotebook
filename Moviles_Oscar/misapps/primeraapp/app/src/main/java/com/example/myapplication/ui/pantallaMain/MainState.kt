package com.example.myapplication.ui.pantallaMain

import com.example.myapplication.domain.modelo.Raton

data class MainState(
    val persona: Raton = Raton("null","null","null",0,0,0, "0/0/0"),
    val error: String? = null
)