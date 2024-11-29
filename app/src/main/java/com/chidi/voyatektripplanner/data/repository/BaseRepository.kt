package com.chidi.voyatektripplanner.data.repository

import com.chidi.voyatektripplanner.data.models.Location
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.data.models.Trip
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    fun getTripDetails(id: Int): Flow<Response<Trip>>

    fun getTrips(): Flow<Response<List<Trip>>>

    fun createTrip(trip: Trip): Flow<Response<Trip>>

    fun getLocations(query: String): Flow<Response<List<Location>>>
}