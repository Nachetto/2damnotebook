package com.example.nachorestaurante.framework.pantalladetallada

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.nachorestaurante.databinding.ActivityDetailedBinding
import com.example.nachorestaurante.framework.pantallamain.CustomerAdapter
import com.example.nachorestaurante.framework.pantallamain.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailedActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailedBinding
    private var primeraVez: Boolean = false
    private lateinit var customAdapter: CustomerAdapter
    private val viewModel: MainViewModel by viewModels()
    private val callback by lazy {
        configContextBar()
    }
}