package dam.nacho.domain.usecases.ratones

import dam.nacho.data.Repository
import dam.nacho.domain.modelo.Raton
class ModifyRatonUseCase {
    operator fun invoke(ratonId:Int, NuevoRaton: Raton):Boolean {
        val repository = Repository.getInstance()
        return repository.modifyRaton(ratonId,NuevoRaton)
    }
}