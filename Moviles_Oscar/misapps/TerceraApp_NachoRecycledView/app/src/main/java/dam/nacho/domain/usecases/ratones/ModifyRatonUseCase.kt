package com.example.myapplication.domain.usecases.ratones

import com.example.myapplication.data.Repository
import com.example.myapplication.domain.modelo.Raton
class ModifyRatonUseCase {
    operator fun invoke(ratonId:Int, NuevoRaton: Raton):Boolean {
        val repository = Repository.getInstance()
        return repository.modifyRaton(ratonId,NuevoRaton)
    }
}