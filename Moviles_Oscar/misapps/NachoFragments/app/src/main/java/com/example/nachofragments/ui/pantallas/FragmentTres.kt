package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentTresBinding

class FragmentTres : Fragment() {

    private var _binding: FragmentTresBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentTresArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentUno.setOnClickListener {
                val action = FragmentTresDirections.actionFragmentTresToFragmentDos("Saludos desde el fragment tres")
                findNavController().navigate(action)
            }
            irAFragmentDos.setOnClickListener {
                val action = FragmentTresDirections.actionFragmentTresToFragmentUno("Saludos desde el fragment tres")
                findNavController().navigate(action)
            }
        }
    }


}