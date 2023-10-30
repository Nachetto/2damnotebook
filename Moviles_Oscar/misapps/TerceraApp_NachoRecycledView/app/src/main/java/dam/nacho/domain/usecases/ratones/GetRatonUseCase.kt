package dam.nacho.domain.usecases.ratones

import dam.nacho.data.Repository
import dam.nacho.domain.modelo.Raton
class GetRatonUseCase {
    operator fun invoke(id: Int): Raton? {
        val repository = Repository.getInstance()
        return repository.getRatonById(id)
    }
}