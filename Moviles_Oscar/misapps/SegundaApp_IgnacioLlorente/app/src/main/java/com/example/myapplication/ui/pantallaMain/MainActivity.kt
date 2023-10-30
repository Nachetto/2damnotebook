package com.example.myapplication.ui.pantallaMain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.AddRatonUseCase
import com.example.myapplication.domain.usecases.ratones.DeleteRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetLastIdFromRatonesListUseCase
import com.example.myapplication.domain.usecases.ratones.GetRatonUseCase
import com.example.myapplication.domain.usecases.ratones.ModifyRatonUseCase


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            AddRatonUseCase(),
            DeleteRatonUseCase(),
            GetRatonUseCase(),
            GetLastIdFromRatonesListUseCase(),
            ModifyRatonUseCase()
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        cargarPrimerRaton()
        obserbarViewModel()
        eventos()
    }


    private fun cargarPrimerRaton() {
        val raton = viewModel.getRaton(1)
        if (!darValoresAlRaton(raton!!)) {
            Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
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
                getString(R.string.blanco) -> radioGroup2?.check(R.id.radioButton)
                getString(R.string.negro) -> radioGroup2?.check(R.id.radioButton2)
                getString(R.string.azul) -> radioGroup2?.check(R.id.radioButton3)
                getString(R.string.rojo) -> radioGroup2?.check(R.id.radioButton4)
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
            val selectedColorId = radioGroup2?.checkedRadioButtonId
            val color = when (selectedColorId) {
                R.id.radioButton -> getString(R.string.blanco)
                R.id.radioButton2 -> getString(R.string.negro)
                R.id.radioButton3 -> getString(R.string.azul)
                R.id.radioButton4 -> getString(R.string.rojo)
                else -> {
                    Toast.makeText(this@MainActivity, "Por favor, seleccione un color", Toast.LENGTH_SHORT).show()
                    return null
                }
            }
            if (modelo.isEmpty() || marca.isEmpty() || pesoText.isEmpty() || dpiText.isEmpty() || idText.isEmpty() || fechaText.isEmpty()) {
                Toast.makeText(this@MainActivity, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return null
            }
            val peso = pesoText.toIntOrNull()
            val dpi = dpiText.toIntOrNull()
            val id = idText.toIntOrNull()
            if (peso == null || dpi == null || id == null) {
                Toast.makeText(this@MainActivity, "Los valores ingresados no son válidos", Toast.LENGTH_SHORT).show()
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


    private fun obserbarViewModel(){
        viewModel.uiState.observe(this@MainActivity) { state ->
            state.error?.let { error ->
                Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                viewModel.errorMostrado()
            }
            if (state.error == null)
                if (state.tipoLlamada.equals("add"))
                    darValoresAlRaton(state.raton)
        }
    }

    private fun eventos(){
        with(binding){
            buttonAdd.setOnClickListener {
                val raton = nuevoRatonDesdePantalla()

                if (raton != null) {
                    if (viewModel.getRaton(raton.id) == null) {
                        viewModel.addRaton(raton)
                        Toast.makeText(this@MainActivity, "Ratón nuevo creado con el ID " + raton.id, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Ya existe un ratón con el mismo ID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error al crear el ratón", Toast.LENGTH_SHORT).show()
                }
            }


            buttonAnterior.setOnClickListener {
                val idActualText = binding.editTextId.text.toString()
                val idActual = idActualText.toIntOrNull()

                if (idActual != null && idActual > 1) {
                    val raton = viewModel.getRaton(idActual - 1)
                    if (raton != null) {
                        darValoresAlRaton(raton)
                    } else {
                        Toast.makeText(this@MainActivity, "El ratón no tiene contenido", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Este es el primer ratón", Toast.LENGTH_SHORT).show()
                }
            }

            buttonSiguiente.setOnClickListener {
                val idActualText = binding.editTextId.text.toString()
                val idActual = idActualText.toIntOrNull()
                val lastId = viewModel.getLastID()

                if (idActual != null && lastId != null) {
                    if (idActual < lastId) {
                        val raton = viewModel.getRaton(idActual + 1)
                        if (raton != null) {
                            darValoresAlRaton(raton)
                        } else {
                            Toast.makeText(this@MainActivity, "El ratón no tiene contenido", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Este es el último ID", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error al leer el ID", Toast.LENGTH_SHORT).show()
                }
            }

            buttonUpdate.setOnClickListener {
                val raton = nuevoRatonDesdePantalla()
                if (raton != null) {
                    val idActual = raton.id
                    val ratonExistente = viewModel.getRaton(idActual)

                    if (ratonExistente != null) {
                        if (viewModel.modifyRaton(idActual, raton)) {
                            Toast.makeText(this@MainActivity, "Ratón actualizado con éxito", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Error al actualizar el ratón", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "No se encontró un ratón con el ID $idActual", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error al crear el ratón", Toast.LENGTH_SHORT).show()
                }
            }

            buttonDelete.setOnClickListener {
                val idActual = binding.editTextId.text.toString().toIntOrNull()

                if (idActual != null) {
                    val ratonAEliminar = viewModel.getRaton(idActual)
                    if (ratonAEliminar != null) {
                        if (viewModel.deleteRaton(ratonAEliminar)) {
                            Toast.makeText(this@MainActivity, "Ratón eliminado con éxito", Toast.LENGTH_SHORT).show()
                            val idAnterior = idActual - 1
                            val ratonAnterior = viewModel.getRaton(idAnterior)
                            if (ratonAnterior != null) {
                                darValoresAlRaton(ratonAnterior)
                            } else {
                                Toast.makeText(this@MainActivity, "No se encontró un ratón con el ID $idAnterior", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Error al eliminar el ratón", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "No se encontró un ratón con el ID $idActual", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "ID no válido", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}