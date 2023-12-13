package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentCuatroBinding

class FragmentCuatro : Fragment() {
    private var _binding: FragmentCuatroBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCuatroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentCuatroArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentCinco.setOnClickListener {
                val action = FragmentCuatroDirections.actionFragmentCuatroToFragmentCinco("Saludos desde el fragment cuatro")
                findNavController().navigate(action)
            }
            irAFragmentSeis.setOnClickListener{
                val action = FragmentCuatroDirections.actionFragmentCuatroToFragmentSeis("Saludos desde el fragment cuatro")
                findNavController().navigate(action)

            }
        }
    }
}