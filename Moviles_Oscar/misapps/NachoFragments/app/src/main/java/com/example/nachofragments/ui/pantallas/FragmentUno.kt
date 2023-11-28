package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentUnoBinding

class FragmentUno : Fragment() {
    private var _binding: FragmentUnoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUnoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentUnoArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentDos.setOnClickListener {
                val action = FragmentUnoDirections.actionFragmentUnoToFragmentDos("Saludos desde el fragment uno")
                findNavController().navigate(action)
            }
            irAFragmentTres.setOnClickListener{
                val action = FragmentUnoDirections.actionFragmentUnoToFragmentTres("Saludos desde el fragment uno")
                findNavController().navigate(action)

            }
        }
    }
}