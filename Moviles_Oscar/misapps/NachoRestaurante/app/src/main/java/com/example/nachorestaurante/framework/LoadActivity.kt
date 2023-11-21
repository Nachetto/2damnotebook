package com.example.nachorestaurante.framework

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nachorestaurante.framework.pantallamain.MainActivity

class LoadActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
    }
}