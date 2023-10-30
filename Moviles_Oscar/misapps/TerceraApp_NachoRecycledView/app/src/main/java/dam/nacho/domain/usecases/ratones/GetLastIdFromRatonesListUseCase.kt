package dam.nacho.domain.usecases.ratones

import dam.nacho.data.Repository
class GetLastIdFromRatonesListUseCase {
    operator fun invoke(): Int? {
        val repository = Repository.getInstance()
        return repository.getLastIdFromRatonesList()
    }
}