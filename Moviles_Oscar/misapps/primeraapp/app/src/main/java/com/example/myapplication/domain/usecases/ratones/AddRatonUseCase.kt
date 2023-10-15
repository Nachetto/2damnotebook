package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton
class AddRatonUseCase {
    operator fun invoke(raton: Raton):Boolean {
        val repository = Repository.getInstance()
        return repository.addRaton(raton)
    }
}
