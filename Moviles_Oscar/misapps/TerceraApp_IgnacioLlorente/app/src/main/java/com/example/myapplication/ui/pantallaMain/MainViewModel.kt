package com.example.myapplication.ui.pantallaMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.domain.usecases.ratones.GetAllRatonesUseCase

class MainViewModel(

    private val getAllRatonesUseCase: GetAllRatonesUseCase
) : ViewModel() {
    private val _uiState = MutableLiveData<MainState>()
    val uiState: LiveData<MainState> get() = _uiState

    init {
        _uiState.value = MainState(
            ratones = getAllRatonesUseCase()
        )
    }


    fun cargarRatones(){
        _uiState.value = _uiState.value?.copy(ratones = getAllRatonesUseCase())
    }

}


/**
 * Factory class to instantiate the [ViewModel] instance.
 */
class MainViewModelFactory(
    private val getAllRatonesUseCase: GetAllRatonesUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                getAllRatonesUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}