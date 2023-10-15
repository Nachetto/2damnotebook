package com.example.myapplication.ui.pantallaMain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.R

class MainActivityMio : AppCompatActivity() {
    private lateinit var editText: TextView
    private lateinit var enga: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = this.findViewById(R.id.metiendotexto)
        enga = this.findViewById(R.id.butaca)
        enga.setOnClickListener {
            if (!editText.text.isNullOrEmpty()) {
                Toast.makeText(this, editText.text, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.invalid, Toast.LENGTH_SHORT).show()
            }
        }
    }
}