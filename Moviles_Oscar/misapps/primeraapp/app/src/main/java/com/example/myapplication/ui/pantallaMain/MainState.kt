package com.example.myapplication.ui.pantallaMain

import com.example.myapplication.domain.modelo.Raton
import java.util.Calendar

data class MainState(
    val persona: Raton = Raton("null","null","null",0,0,0, Calendar.getInstance()),
    val error: String? = null
)