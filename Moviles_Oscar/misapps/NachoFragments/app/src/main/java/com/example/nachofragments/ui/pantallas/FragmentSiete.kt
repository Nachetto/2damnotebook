package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentSieteBinding

class FragmentSiete : Fragment() {
    private var _binding: FragmentSieteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSieteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentSieteArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentOcho.setOnClickListener {
                val action = FragmentSieteDirections.actionFragmentSieteToFragmentOcho("Saludos desde el fragment Siete")
                findNavController().navigate(action)
            }
            irAFragmentNueve.setOnClickListener{
                val action = FragmentSieteDirections.actionFragmentSieteToFragmentNueve("Saludos desde el fragment Siete")
                findNavController().navigate(action)

            }
        }
    }
}