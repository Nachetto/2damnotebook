package com.example.myapplication.ui.pantallaMain

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.AddRatonUseCase
import com.example.myapplication.domain.usecases.ratones.DeleteRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetLastIdFromRatonesListUseCase
import com.example.myapplication.domain.usecases.ratones.GetRatonUseCase
import com.example.myapplication.utils.StringProvider



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
        }
    }
}