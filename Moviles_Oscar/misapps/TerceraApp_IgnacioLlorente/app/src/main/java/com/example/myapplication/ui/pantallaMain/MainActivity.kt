package com.example.myapplication.ui.pantallaMain

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.data.Repository
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.GetAllRatonesUseCase
import com.example.myapplication.ui.pantallaRaton.RatonActivity
import com.example.myapplication.ui.pantallaRaton.adapter.RatonAdapter


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RatonAdapter

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            GetAllRatonesUseCase(
                Repository(assets.open("ratones.json"))
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }

        observarViewModel()
    }

    private fun observarViewModel() {
        viewModel.uiState.observe(this) {  state ->
            state.ratones.let { lista ->
                adapter = RatonAdapter(lista) { cargarRatonSeleccionado(it) }
                binding.rvRatones.adapter = adapter
            }
        }
    }

    private fun cargarRatonSeleccionado(raton: Raton) {
        val intent = Intent(this, RatonActivity::class.java)
        intent.putExtra("RatonActivity:raton", raton)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.cargarRatones()
    }
}