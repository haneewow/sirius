package com.example.sirius.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sirius.domain.model.data.DeveloperNote
import com.example.sirius.domain.model.data.Result
import com.example.sirius.domain.model.usecase.interfaces.ClearDataUseCase
import com.example.sirius.domain.model.usecase.interfaces.GetNoteUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getNoteUseCase: GetNoteUseCase,
    private val clearDataUseCase: ClearDataUseCase
) : ViewModel() {
    private val _notes = MutableLiveData<Result<DeveloperNote?>>()
    val notes: LiveData<Result<DeveloperNote?>> get() = _notes

    private val _buttonEnabled = MutableLiveData(false)
    val buttonEnabled: LiveData<Boolean> get() = _buttonEnabled

    fun loadNotes(number: Int) = viewModelScope.launch(IO) {
        getNoteUseCase.invoke(number)
            .collect {
                _notes.postValue(it)
                if (number == FIRST_PAGE) _buttonEnabled.postValue(false)
                else _buttonEnabled.postValue(true)
            }
    }

    fun clearData() = viewModelScope.launch(IO) {
        clearDataUseCase.invoke()
    }

    companion object {
        private const val FIRST_PAGE = 1
    }
}