package com.example.myapplication.ui.pantallaRaton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityRatonBinding
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.AddRatonUseCase
import com.example.myapplication.domain.usecases.ratones.DeleteRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetRatonUseCase
import com.example.myapplication.domain.usecases.ratones.ModifyRatonUseCase

class RatonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRatonBinding

    private val viewModel: RatonViewModel by viewModels {
        RatonViewModelFactory(
            AddRatonUseCase(),
            DeleteRatonUseCase(),
            GetRatonUseCase(),
            ModifyRatonUseCase()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRatonBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        cargarRatonSeleccionado()
        eventos()
        observarViewModel()
    }

    @Suppress("DEPRECATION")
    private fun cargarRatonSeleccionado() {
        val raton = intent.getParcelableExtra<Raton>("RatonActivity:raton")

        if (raton != null) {
            darValoresAlRaton(
                Raton(
                    raton.modelo,
                    raton.marca,
                    raton.color,
                    raton.peso,
                    raton.DPI,
                    raton.id,
                    raton.fechaFabricacion
                )
            )
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun darValoresAlRaton(raton: Raton): Boolean {
        with(binding) {
            editTextModelo.setText(raton.modelo)
            editTextMarca.setText(raton.marca)
            editTextPeso.setText(raton.peso.toString())
            editTextDpi.setText(raton.DPI.toString())
            editTextId.setText(raton.id.toString())
            editTextDate.setText(raton.fechaFabricacion)
            when (raton.color) {
                getString(R.string.blanco) -> radioGroup2.check(R.id.radioButton)
                getString(R.string.negro) -> radioGroup2.check(R.id.radioButton2)
                getString(R.string.azul) -> radioGroup2.check(R.id.radioButton3)
                getString(R.string.rojo) -> radioGroup2.check(R.id.radioButton4)
            }

            return true
        }
    }

    private fun nuevoRatonDesdePantalla(): Raton? {
        with(binding) {
            val modelo = editTextModelo.text.toString()
            val marca = editTextMarca.text.toString()
            val pesoText = editTextPeso.text.toString()
            val dpiText = editTextDpi.text.toString()
            val idText = editTextId.text.toString()
            val fechaText = editTextDate.text.toString()
            val selectedColorId = radioGroup2.checkedRadioButtonId
            val color = when (selectedColorId) {
                R.id.radioButton -> getString(R.string.blanco)
                R.id.radioButton2 -> getString(R.string.negro)
                R.id.radioButton3 -> getString(R.string.azul)
                R.id.radioButton4 -> getString(R.string.rojo)
                else -> {
                    Toast.makeText(
                        this@RatonActivity,
                        "Por favor, seleccione un color",
                        Toast.LENGTH_SHORT
                    ).show()
                    return null
                }
            }
            if (modelo.isEmpty() || marca.isEmpty() || pesoText.isEmpty() || dpiText.isEmpty() || idText.isEmpty() || fechaText.isEmpty()) {
                Toast.makeText(
                    this@RatonActivity,
                    "Por favor, complete todos los campos obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
                return null
            }
            val peso = pesoText.toIntOrNull()
            val dpi = dpiText.toIntOrNull()
            val id = idText.toIntOrNull()
            if (peso == null || dpi == null || id == null) {
                Toast.makeText(
                    this@RatonActivity,
                    "Los valores ingresados no son válidos",
                    Toast.LENGTH_SHORT
                ).show()
                return null
            }
            return Raton(
                modelo = modelo,
                marca = marca,
                color = color,
                peso = peso,
                DPI = dpi,
                id = id,
                fechaFabricacion = fechaText
            )
        }
    }


    private fun observarViewModel() {
        viewModel.uiState.observe(this) { state ->
            state.mensaje?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun eventos() {
        with(binding) {

            //AÑADIR
            buttonAdd.setOnClickListener {
                val raton = nuevoRatonDesdePantalla()
                viewModel.addRaton(raton)
            }

            //ACTUALIZAR
            buttonUpdate.setOnClickListener {
                val raton = nuevoRatonDesdePantalla()
                val idActual = binding.editTextId.text.toString().toIntOrNull()
                if (idActual == null) {
                    Toast.makeText(this@RatonActivity, "ID no válido", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.modifyRaton(idActual, raton)
            }

            //ELIMINAR
            buttonDelete.setOnClickListener {
                val idActual = binding.editTextId.text.toString().toIntOrNull()
                idActual?.let {
                    viewModel.getRaton(it)?.let { it1 -> viewModel.deleteRaton(it1) }
                }
            }

            //VOLVER
            binding.buttonMostrarTabla.setOnClickListener {
                finish()
            }
        }
    }
}