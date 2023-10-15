package com.example.myapplication.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.myapplication.domain.modelo.Raton

class StringProvider(val context: Context) {
    companion object {
        fun instance(context: Context): StringProvider = StringProvider(context)
    }

    fun getString(@StringRes stringResId: Int): String {
        return context.getString(stringResId)
    }

}