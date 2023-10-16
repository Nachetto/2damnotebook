package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
class GetLastIdFromRatonesListUseCase {
    operator fun invoke(): Int? {
        val repository = Repository.getInstance()
        return repository.getLastIdFromRatonesList()
    }
}