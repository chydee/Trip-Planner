package com.chidi.voyatektripplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chidi.voyatektripplanner.ui.MainPage
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VoyatekTripPlannerTheme() {
                MainPage()
            }
        }
    }
}

@Serializable
sealed interface Pages {

    @Serializable
    data object Landing : Pages

    @Serializable
    data class TripDetails(val id: Int) : Pages
}