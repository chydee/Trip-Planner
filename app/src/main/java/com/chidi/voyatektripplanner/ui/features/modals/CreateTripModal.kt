package com.chidi.voyatektripplanner.ui.features.modals

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.data.models.TravelStyle
import com.chidi.voyatektripplanner.ui.components.InputBox
import com.chidi.voyatektripplanner.ui.components.MainButton
import com.chidi.voyatektripplanner.ui.components.OptionsSelector
import com.chidi.voyatektripplanner.ui.components.TitleBox
import com.chidi.voyatektripplanner.ui.features.landing.LandingPageViewModel
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTripModal(
    viewModel: LandingPageViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
    onClose: () -> Unit,
    onShowMessage: (String) -> Unit
) {
    LaunchedEffect(key1 = 1) {
        viewModel.resetResponse()
    }
    val response = viewModel.tripCreateResponse.collectAsState()
    if (response.value?.status == Response.Status.SUCCESS) {
        viewModel.trip.reset()
        onClose()
    }

    ModalBottomSheet(
        onDismissRequest = onClose,
        dragHandle = {},
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 28.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.tree_palm),
                    contentDescription = null,
                    modifier = Modifier
                        .size(42.dp)
                        .background(
                            color = LocalCustomColors.current.blueLight80
                        )
                        .padding(8.dp),
                    tint = LocalCustomColors.current.blue60
                )
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .size(26.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.close),
                        contentDescription = null,
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
            TitleBox(
                titleResId = R.string.create_trip,
                subTextResId = R.string.build_adventure
            )
            InputBox(
                titleResId = R.string.trip_name,
                hintResId = R.string.enter_trip_name
            ) {
                viewModel.trip.title = it
            }
            Text(
                stringResource(R.string.travel_style),
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(
                    top = 14.dp, bottom = 6.dp
                )
            )
            OptionsSelector(
                options = TravelStyle.entries.map { it.textResId },
                selectedOption = null,
                placeHolderTextResId = R.string.select_travel_style
            ) {
                viewModel.trip.travelStyle = TravelStyle.valueOf(
                    context.getString(it)
                )
            }
            InputBox(
                titleResId = R.string.trip_description,
                hintResId = R.string.tell_us_more,
                maxLines = 6
            ) {
                viewModel.trip.description = it
            }
            Box(
                contentAlignment = Alignment.Center
            ) {
                MainButton(
                    buttonTextResId = R.string.next,
                    modifier = Modifier.padding(top = 16.dp),
                    enabled = response.value?.status != Response.Status.LOADING
                ) {
                    if (viewModel.trip.canCreate)
                        viewModel.createTrip()
                    else {
                        onShowMessage(context.getString(R.string.fields_required))
                    }
                }
                if (response.value?.status == Response.Status.LOADING) {
                    CircularProgressIndicator()
                }
            }

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
@Preview
fun CreateTripPreview() {
    VoyatekTripPlannerTheme() {
        Scaffold {
            Column {
                CreateTripModal(
                    onClose = {}
                ) {

                }
            }
        }
    }
}