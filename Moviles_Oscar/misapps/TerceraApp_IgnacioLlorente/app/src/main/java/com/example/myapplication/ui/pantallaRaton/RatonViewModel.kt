package com.example.myapplication.ui.pantallaRaton

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.AddRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetRatonUseCase
import com.example.myapplication.domain.usecases.ratones.DeleteRatonUseCase
import com.example.myapplication.domain.usecases.ratones.ModifyRatonUseCase

class RatonViewModel(
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val modifyRatonUseCase: ModifyRatonUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<RatonState>()
    val uiState: LiveData<RatonState> get() = _uiState
    fun addRaton(raton: Raton?) {
        if (raton != null) {
            if (!addRatonUseCase(raton)) {
                _uiState.value = RatonState()
                _uiState.value = RatonState(mensaje = Constantes.ERROR)
                _uiState.value = RatonState(
                    mensaje = "error"
                )
                _uiState.value = RatonState(mensaje = Constantes.ERROR)
            } else {
                _uiState.value = RatonState(mensaje = Constantes.RATON_ANADIDO)
            }
        }
    }


    fun modifyRaton(id: Int, nuevoraton: Raton?) {
        if (nuevoraton == null)
            _uiState.value = _uiState.value?.copy(mensaje = Constantes.ERROR)
        else {
            if (modifyRatonUseCase(id, nuevoraton))
                _uiState.value = RatonState(mensaje = Constantes.RATON_MODIFICADO)
            else
                _uiState.value = RatonState(mensaje = Constantes.ERROR)
        }
    }

    fun deleteRaton(raton: Raton) {
        if (deleteRatonUseCase(raton))
            _uiState.value = RatonState(Constantes.RATON_ELIMINADO)
        else
            _uiState.value = RatonState(mensaje = Constantes.ERROR)
    }

    fun getRaton(id: Int): Raton? {
        return getRatonUseCase(id)
    }
}

/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class RatonViewModelFactory(
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val modifyRatonUseCase: ModifyRatonUseCase

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RatonViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RatonViewModel(
                addRatonUseCase,
                deleteRatonUseCase,
                getRatonUseCase,
                modifyRatonUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}