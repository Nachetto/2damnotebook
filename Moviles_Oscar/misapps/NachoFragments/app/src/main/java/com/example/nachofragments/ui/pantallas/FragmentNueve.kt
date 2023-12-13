package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentNueveBinding

class FragmentNueve : Fragment() {

    private var _binding: FragmentNueveBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNueveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentNueveArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentSiete.setOnClickListener {
                val action = FragmentNueveDirections.actionFragmentNueveToFragmentSiete("Saludos desde el fragment nueve")
                findNavController().navigate(action)
            }
            irAFragmentOcho.setOnClickListener {
                val action = FragmentNueveDirections.actionFragmentNueveToFragmentOcho("Saludos desde el fragment nueve")
                findNavController().navigate(action)
            }
        }
    }


}