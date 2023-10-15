package com.example.myapplication.domain.usecases.ratones


import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton

class DeleteRatonUseCase {
    operator fun invoke(raton: Raton) {
        val repository = Repository.getInstance()
        repository.deleteRaton(raton)
    }
}