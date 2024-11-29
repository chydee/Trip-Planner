package com.chidi.voyatektripplanner.ui.features.details

import androidx.lifecycle.ViewModel
import com.chidi.voyatektripplanner.data.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TripDetailsViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {

    private val _id = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val trip = _id.flatMapLatest {
        repository.getTrip(it)
    }

    fun getTrip(id: Int) {
        _id.value = id
    }
}