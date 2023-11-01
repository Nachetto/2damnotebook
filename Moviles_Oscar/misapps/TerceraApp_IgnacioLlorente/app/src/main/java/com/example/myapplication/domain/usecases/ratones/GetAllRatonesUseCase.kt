package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton

class GetAllRatonesUseCase (private val repository: Repository){

    operator fun invoke():List<Raton> {
        return repository.getAllRatones()
    }
}