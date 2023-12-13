package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentOchoBinding

class FragmentOcho : Fragment() {

    private var _binding: FragmentOchoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOchoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentOchoArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentSiete.setOnClickListener {
                val action = FragmentOchoDirections.actionFragmentOchoToFragmentSiete("Saludos desde el fragment ocho")
                findNavController().navigate(action)
            }
            irAFragmentNueve.setOnClickListener {
                val action = FragmentOchoDirections.actionFragmentOchoToFragmentNueve("Saludos desde el fragment ocho")
                findNavController().navigate(action)

            }
        }
    }
}

