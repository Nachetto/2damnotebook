package dam.nacho.domain.usecases.ratones

import dam.nacho.data.Repository
import dam.nacho.domain.modelo.Raton
class AddRatonUseCase {
    operator fun invoke(raton: Raton):Boolean {
        val repository = Repository.getInstance()
        return repository.addRaton(raton)
    }
}
