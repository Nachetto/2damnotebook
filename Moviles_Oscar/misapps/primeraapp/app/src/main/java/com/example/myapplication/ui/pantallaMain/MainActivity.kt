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
import com.example.myapplication.utils.StringProvider
import java.util.Calendar


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            StringProvider.instance(this),
            AddRatonUseCase(),
            DeleteRatonUseCase(),
            GetRatonUseCase(),
            GetLastIdFromRatonesListUseCase()
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
        val raton = Raton(
            modelo = "Modelo Ejemplo",
            marca = "Marca Ejemplo",
            color = "Negro",
            peso = 100,
            DPI = 1600,
            id = 1,
            fechaFabricacion = "1/1/1"
        )

        if (!darValoresAlRaton(raton)) {
            Toast.makeText(this@MainActivity, "error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun darValoresAlRaton(raton: Raton): Boolean {
        with(binding) {
            // Asigna los valores del objeto Raton a los campos de entrada
            editTextModelo.setText(raton.modelo)
            editTextMarca.setText(raton.marca)
            editTextPeso.setText(raton.peso.toString())
            editTextDpi.setText(raton.DPI.toString())
            editTextDId?.setText(raton.id.toString())
            editTextDate.setText(raton.fechaFabricacion)

            // Asigna el color seleccionado en el objeto Raton a los RadioButtons
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
            val idText = editTextDId?.text.toString()
            val fechaText = editTextDate.text.toString()

            // Obtiene el ID del RadioButton seleccionado en el RadioGroup
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

            // Verifica si los campos obligatorios no están vacíos
            if (modelo.isEmpty() || marca.isEmpty() || pesoText.isEmpty() || dpiText.isEmpty() || idText.isEmpty() || fechaText.isEmpty()) {
                Toast.makeText(this@MainActivity, "Por favor, complete todos los campos obligatorios", Toast.LENGTH_SHORT).show()
                return null
            }

            // Intenta convertir los valores a los tipos correctos
            val peso = pesoText.toIntOrNull()
            val dpi = dpiText.toIntOrNull()
            val id = idText.toIntOrNull()

            // Verifica si las conversiones fueron exitosas
            if (peso == null || dpi == null || id == null) {
                Toast.makeText(this@MainActivity, "Los valores ingresados no son válidos", Toast.LENGTH_SHORT).show()
                return null
            }

            // Crea un nuevo objeto Raton con los valores obtenidos
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
                null
                //cambiar los valores al nuevo raton
                //editTextTextPersonName.setText(state.persona.nombre)
        }
    }

    private fun eventos(){
        with(binding){
            buttonAdd.setOnClickListener {
                //cuando se clickea el boton add, me crea la persona
                //viewModel.addRaton(Raton())
            }
            /*buttonUpdate.setOnClickListener{
                //cuando se clickea el boton update, ,
            }*/
        }
    }
}