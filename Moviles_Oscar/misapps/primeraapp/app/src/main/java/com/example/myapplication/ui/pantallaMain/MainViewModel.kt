package com.example.myapplication.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.appnobasica.ui.pantallaMain.Constantes
import com.example.myapplication.R
import com.example.myapplication.domain.modelo.Raton
import com.example.myapplication.domain.usecases.ratones.AddRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetRatonUseCase
import com.example.myapplication.utils.StringProvider
import com.example.myapplication.domain.usecases.ratones.DeleteRatonUseCase
import com.example.myapplication.domain.usecases.ratones.GetLastIdFromRatonesListUseCase

class MainViewModel(
    //los usecases que vayas a usar en los metodos de abajo y el Stringprovider que no se lo que es
    private val stringProvider: StringProvider,
    //los usecases
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val getLastIdFromRatonesListUseCase: GetLastIdFromRatonesListUseCase
) : ViewModel() {
    //esto es algo del estado que es inmutable y se hace esto
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    //todo aqui haces las funciones de anadir editar y eliminar ratones
    fun addRaton(raton: Raton) {
        if (!addRatonUseCase(raton)) {
            _uiState.value = MainState(
                error = "error"//stringProvider.getString(R.string.name),
            )
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }

    fun deleteRaton(raton: Raton){
        if (!deleteRatonUseCase(raton)) {
            _uiState.value = MainState(
                error = "error"//stringProvider.getString(R.string.name),
            )
            _uiState.value = _uiState.value?.copy(error = Constantes.ERROR)
        }
    }


    fun getRaton(id:Int){
        var raton = getRatonUseCase(id)
        //lo envias al state?
    }

//    fun getPersonas(id: Int) {
//        val personas = getPersonas()
//
//        if (personas.size < id || id < 0) {
//            _uiState.value = _uiState.value?.copy(error = "error")
//
//        } else
//            _uiState.value = _uiState.value?.copy(raton = personas[id])
//    }

    fun getLastID() {
        var id =getLastIdFromRatonesListUseCase
        //lo igualas al state
    }

    fun errorMostrado() {
        _uiState.value = _uiState.value?.copy(error = null)
    }



}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(
    private val stringProvider: StringProvider,
    private val addRatonUseCase: AddRatonUseCase,
    private val deleteRatonUseCase: DeleteRatonUseCase,
    private val getRatonUseCase: GetRatonUseCase,
    private val getLastIdFromRatonesListUseCase: GetLastIdFromRatonesListUseCase

    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                stringProvider,
                addRatonUseCase,
                deleteRatonUseCase,
                getRatonUseCase,
                getLastIdFromRatonesListUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}