package com.chidi.voyatektripplanner.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.chidi.voyatektripplanner.Pages
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.ui.features.details.TripDetailsScreen
import com.chidi.voyatektripplanner.ui.features.landing.LandingPage
import com.chidi.voyatektripplanner.ui.states.AppState
import com.chidi.voyatektripplanner.ui.states.rememberAppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(
    appState: AppState = rememberAppState()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(
                            if (appState.currentRoute.value is Pages.Landing)
                                R.string.plan_trip
                            else R.string.trip_details
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            appState.navController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow_left),
                            contentDescription = null
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) }
    ) { innerPadding ->
        NavHost(
            modifier = Modifier.padding(innerPadding),
            navController = appState.navController,
            startDestination = Pages.Landing
        ) {
            composable<Pages.Landing> {
                LandingPage(
                    onShowMessage = {
                        appState.showMessage(it)
                    }
                ) { id ->
                    appState.navController.navigate(
                        Pages.TripDetails(id)
                    )
                }
            }
            composable<Pages.TripDetails>(
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(500),
                        towards = AnimatedContentTransitionScope.SlideDirection.End
                    )
                },
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(500),
                        towards = AnimatedContentTransitionScope.SlideDirection.Start
                    )
                }
            ) {
                TripDetailsScreen(
                    id = it.toRoute<Pages.TripDetails>().id
                )
            }
        }
    }
}