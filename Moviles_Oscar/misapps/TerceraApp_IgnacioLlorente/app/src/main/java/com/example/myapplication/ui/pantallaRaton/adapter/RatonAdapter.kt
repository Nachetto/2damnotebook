package com.example.myapplication.ui.pantallaRaton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRatonBinding
import com.example.myapplication.domain.modelo.Raton


class RatonAdapter(
    private var raton: List<Raton>,
    private val ratonBotonListener: (Raton) -> Unit
) : RecyclerView.Adapter<RatonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatonViewHolder {
        val binding = ItemRatonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RatonViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RatonViewHolder, position: Int) {
        val raton = raton[position]
        holder.bind(raton, ratonBotonListener)
        holder.itemView.setOnClickListener {
            ratonBotonListener(raton)
        }
    }

    override fun getItemCount() = raton.size
}

class RatonViewHolder(private val binding: ItemRatonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        raton: Raton,
        onClickButton: (Raton) -> Unit
    ) {
        binding.txtNombrePokemon.text = raton.modelo
        binding.buttonPokemon.setOnClickListener {
            onClickButton(raton)
        }
    }
}

