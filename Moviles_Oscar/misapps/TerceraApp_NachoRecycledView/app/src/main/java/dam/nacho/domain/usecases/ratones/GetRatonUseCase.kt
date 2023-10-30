package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton
class GetRatonUseCase {
    operator fun invoke(id: Int): Raton? {
        val repository = Repository.getInstance()
        return repository.getRatonById(id)
    }
}