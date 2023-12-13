package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentDosBinding

class FragmentDos : Fragment() {

    private var _binding: FragmentDosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentDosArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentUno.setOnClickListener {
                val action = FragmentDosDirections.actionFragmentDosToFragmentUno("Saludos desde el fragment dos")
                findNavController().navigate(action)
            }
            irAFragmentTres.setOnClickListener {
                val action = FragmentDosDirections.actionFragmentDosToFragmentTres("Saludos desde el fragment dos")
                findNavController().navigate(action)

            }
        }
    }
}

