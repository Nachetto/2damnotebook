package com.example.nachofragments.ui.pantallas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.nachofragments.databinding.FragmentSeisBinding

class FragmentSeis : Fragment() {

    private var _binding: FragmentSeisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSeisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val args : FragmentSeisArgs by navArgs()
            args.mensaje?.let {
                textoDescriptivo.text = it
            }
            irAFragmentCuatro.setOnClickListener {
                val action = FragmentSeisDirections.actionFragmentSeisToFragmentCuatro("Saludos desde el fragment Seis")
                findNavController().navigate(action)
            }
            irAFragmentCinco.setOnClickListener {
                val action = FragmentSeisDirections.actionFragmentSeisToFragmentCinco("Saludos desde el fragment Seis")
                findNavController().navigate(action)

            }
        }
    }
}

