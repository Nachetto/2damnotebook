package dam.nacho.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dam.nacho.ui.pantallaMain.MainState
import dam.nacho.domain.modelo.Raton
import dam.nacho.domain.usecases.ratones.AddRatonUseCase
import dam.nacho.domain.usecases.ratones.GetRatonUseCase
import dam.nacho.domain.usecases.ratones.DeleteRatonUseCase
import dam.nacho.domain.usecases.ratones.GetLastIdFromRatonesListUseCase
import dam.nacho.domain.usecases.ratones.ModifyRatonUseCase

class MainViewModel(
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val getLastIdFromRatonesListUseCase: GetLastIdFromRatonesListUseCase,
    private val modifyRatonUseCase: ModifyRatonUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    fun addRaton(raton: Raton) {
        if (!addRatonUseCase(raton)) {
            _uiState.value = MainState(
                error = "error"
            )
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }

    fun deleteRaton(raton: Raton):Boolean = this.deleteRatonUseCase(raton)


    fun getRaton(id:Int):Raton?{
        return getRatonUseCase(id)
    }

    fun getLastID():Int? {
         return getLastIdFromRatonesListUseCase()
        //lo igualas al state
    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }

    fun modifyRaton(id:Int, nuevoraton:Raton):Boolean{
        return modifyRatonUseCase(id,nuevoraton)
    }


}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val getLastIdFromRatonesListUseCase: GetLastIdFromRatonesListUseCase,
    private val modifyRatonUseCase: ModifyRatonUseCase

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                addRatonUseCase,
                deleteRatonUseCase,
                getRatonUseCase,
                getLastIdFromRatonesListUseCase,
                modifyRatonUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}