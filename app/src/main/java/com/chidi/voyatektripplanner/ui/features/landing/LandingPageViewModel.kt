package com.chidi.voyatektripplanner.ui.features.landing

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.data.models.Trip
import com.chidi.voyatektripplanner.data.repository.BaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingPageViewModel @Inject constructor(
    private val repository: BaseRepository
) : ViewModel() {
    val trip = Trip(0)

    private val _trips =
        MutableStateFlow<Response<List<Trip>>>(Response.loading())
    val trips = _trips.asStateFlow().apply {
        value.data?.lastOrNull()?.let {
            trip.id = it.id
        }
    }
    private val _tripCreateResponse =
        MutableStateFlow<Response<Trip>?>(null)
    val tripCreateResponse = _tripCreateResponse.asStateFlow()

    private val _query = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val locations = _query.flatMapLatest {
        repository.getLocations(it)
    }

    fun createTrip() {
        viewModelScope.launch {
            _tripCreateResponse.emitAll(
                repository.createTrip(
                    trip.apply {
                        id = _trips.value.data?.size?.plus(1) ?: 1
                    }
                )
            )
        }
    }

    fun getAllTrips() {
        viewModelScope.launch {
            _trips.emitAll(repository.getAllTrips())
        }
    }

    fun getLocations(query: String) {
        _query.value = query
    }

    fun resetResponse() {
        viewModelScope.launch {
            _tripCreateResponse.emit(null)
        }
    }
}