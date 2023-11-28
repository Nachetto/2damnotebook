package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentCincoBinding

class FragmentCinco : Fragment() {

    private var _binding: FragmentCincoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCincoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentCincoArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentCuatro.setOnClickListener {
                val action = FragmentCincoDirections.actionFragmentCincoToFragmentCuatro("Saludos desde el fragment cinco")
                findNavController().navigate(action)
            }
            irAFragmentSeis.setOnClickListener {
                val action = FragmentCincoDirections.actionFragmentCincoToFragmentSeis("Saludos desde el fragment cinco")
                findNavController().navigate(action)

            }
        }
    }
}

