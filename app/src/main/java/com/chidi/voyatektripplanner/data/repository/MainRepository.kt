package com.chidi.voyatektripplanner.data.repository

import com.chidi.voyatektripplanner.data.ApiService
import com.chidi.voyatektripplanner.data.models.Location
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.data.models.Trip
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val apiService: ApiService
) : BaseRepository {

    override fun getTrip(id: Int): Flow<Response<Trip>> =
        apiService.getTrip(id).execute()

    override fun getAllTrips(): Flow<Response<List<Trip>>> =
        apiService.getAllTrips().execute()

    override fun createTrip(trip: Trip): Flow<Response<Trip>> =
        apiService.createTrip(trip).execute()

    override fun getLocations(query: String): Flow<Response<List<Location>>> =
        apiService.getLocations(query).execute()
}