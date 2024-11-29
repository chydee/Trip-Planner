package com.chidi.voyatektripplanner.ui.features.landing

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.ui.components.CenterText
import com.chidi.voyatektripplanner.ui.components.InputButton
import com.chidi.voyatektripplanner.ui.components.MainButton
import com.chidi.voyatektripplanner.ui.components.OptionsSelector
import com.chidi.voyatektripplanner.ui.components.RetryBox
import com.chidi.voyatektripplanner.ui.components.TitleBox
import com.chidi.voyatektripplanner.ui.components.TripViewBox
import com.chidi.voyatektripplanner.ui.features.modals.CreateTripModal
import com.chidi.voyatektripplanner.ui.features.modals.DatePicker
import com.chidi.voyatektripplanner.ui.features.modals.LocationModal
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme
import com.chidi.voyatektripplanner.ui.utils.format

@Composable
fun LandingPage(
    viewModel: LandingPageViewModel = hiltViewModel<LandingPageViewModel>(),
    context: Context = LocalContext.current,
    onShowMessage: (String) -> Unit,
    onShowDetails: (Int) -> Unit
) {
    LaunchedEffect(key1 = 1) {
        viewModel.getAllTrips()
    }
    var city by remember {
        mutableStateOf<String?>(null)
    }
    var startDate by remember {
        mutableStateOf<String?>(null)
    }
    var endDate by remember {
        mutableStateOf<String?>(null)
    }

    var showLocationPopup by remember {
        mutableStateOf(false)
    }
    var showDatePopup by remember {
        mutableStateOf(false)
    }
    var showCreateTrip by remember {
        mutableStateOf(false)
    }

    if (showLocationPopup) {
        LocationModal(
            onClose = { showLocationPopup = false }
        ) {
            viewModel.trip.location = it
            city = it.name
            showLocationPopup = false
        }
    }

    if (showDatePopup) {
        DatePicker(
            onClose = { showDatePopup = false }
        ) {
            viewModel.trip.startDate = it.first
            viewModel.trip.endDate = it.second
            startDate = it.first?.format()
            endDate = it.second?.format()
            showDatePopup = false
        }
    }

    if (showCreateTrip) {
        CreateTripModal(
            onClose = { showCreateTrip = false },
            onShowMessage = onShowMessage
        )
    }

    val trips = viewModel.trips.collectAsState()
    val backColor = LocalCustomColors.current.blueLight10

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        Text(
            stringResource(R.string.plan_your_dream_trip),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(backColor)
                }
                .padding(
                    start = 16.dp, end = 16.dp,
                    top = 32.dp, bottom = 12.dp
                )
        )
        Text(
            stringResource(R.string.plan_your_dream_trip_sub_text),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .fillMaxWidth()
                .drawBehind {
                    drawRect(backColor)
                }
                .padding(
                    start = 16.dp, end = 16.dp, bottom = 48.dp
                )
        )
        Box(
            contentAlignment = Alignment.Center
        ) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawRect(backColor)
                        }
                ) {
                    Image(
                        painter = painterResource(R.drawable.trip_back_main),
                        contentDescription = null,
                        modifier = Modifier.weight(0.6f),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        painter = painterResource(R.drawable.cloud),
                        contentDescription = null,
                        tint = LocalCustomColors.current.white,
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(top = 16.dp)
                    )
                }
                Image(
                    painter = painterResource(R.drawable.trip_back_image_bottom),
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 24.dp, end = 24.dp, top = 20.dp
                    )
                    .drawBehind {
                        drawRoundRect(
                            color = Color.White,
                            cornerRadius = CornerRadius(16f)
                        )
                    }
                    .padding(16.dp)

            ) {
                InputButton(
                    textResId = R.string.where_to,
                    subTextResId = R.string.select_city,
                    iconResId = R.drawable.location_outline,
                    text = city
                ) {
                    showLocationPopup = true
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    InputButton(
                        textResId = R.string.start_date,
                        subTextResId = R.string.enter_date,
                        iconResId = R.drawable.calender_grey,
                        modifier = Modifier.weight(1f),
                        text = startDate
                    ) {
                        showDatePopup = true
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    InputButton(
                        textResId = R.string.end_date,
                        subTextResId = R.string.enter_date,
                        iconResId = R.drawable.calender_grey,
                        modifier = Modifier.weight(1f),
                        text = endDate
                    ) {
                        showDatePopup = true
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                MainButton(
                    buttonTextResId = R.string.create_trip,
                    textStyle = MaterialTheme.typography.titleSmall
                ) {
                    if (viewModel.trip.isInitSet)
                        showCreateTrip = true
                    else {
                        onShowMessage(context.getString(R.string.fields_required))
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp, vertical = 8.dp
            )
        ) {
            TitleBox(
                titleResId = R.string.your_trips,
                subTextResId = R.string.trip_itineraries_details2
            )
            OptionsSelector(
                modifier = Modifier.padding(vertical = 12.dp),
                options = listOf(
                    R.string.planned_trips,
                    R.string.trip_itineraries
                ),
                selectedOption = R.string.planned_trips,
                placeHolderTextResId = R.string.planned_trips,
                borderStroke = BorderStroke(
                    width = 12.dp,
                    color = LocalCustomColors.current.white60
                )
            ) {}
            when (trips.value.status) {
                Response.Status.SUCCESS -> {
                    if (trips.value.data.isNullOrEmpty()) {
                        CenterText(textResId = R.string.no_trips)
                    } else {
                        trips.value.data!!.take(5).forEach {
                            TripViewBox(trip = it) { id ->
                                onShowDetails(id)
                            }
                        }
                    }
                }

                Response.Status.ERROR -> RetryBox {
                    viewModel.getAllTrips()
                }

                Response.Status.LOADING ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CircularProgressIndicator()
                    }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun HomeScreenPreview() {
    VoyatekTripPlannerTheme() {
        Scaffold {
            LandingPage(
                onShowMessage = {}
            ) {
            }
        }
    }
}