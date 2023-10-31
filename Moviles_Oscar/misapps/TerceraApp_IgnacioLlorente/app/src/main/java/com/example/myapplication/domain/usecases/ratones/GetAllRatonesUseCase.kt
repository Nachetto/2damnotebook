package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton

class GetAllRatonesUseCase {

    operator fun invoke():List<Raton> {
        val repository = Repository.getInstance()
        return repository.getAllRatones()
    }
}