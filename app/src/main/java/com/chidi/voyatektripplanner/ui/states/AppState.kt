package com.chidi.voyatektripplanner.ui.states

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.chidi.voyatektripplanner.Pages
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    scope: CoroutineScope = rememberCoroutineScope(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController()
): AppState {

    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = remember {
        derivedStateOf {
            backStackEntry.value?.destination?.run {
                if (hasRoute(Pages.TripDetails::class))
                    return@derivedStateOf backStackEntry
                        .value!!.toRoute<Pages.TripDetails>()
            }
            Pages.Landing
        }
    }

    return remember {
        AppState(
            scope = scope,
            snackbarHostState = snackbarHostState,
            navController = navController,
            currentRoute = currentRoute
        )
    }
}

class AppState(
    private val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
    val navController: NavHostController,
    val currentRoute: State<Pages>
) {

    fun showMessage(msg: String) {
        Log.d("AppState", "Show message $msg")
        scope.launch {
            snackbarHostState.showSnackbar(msg)
        }
    }
}