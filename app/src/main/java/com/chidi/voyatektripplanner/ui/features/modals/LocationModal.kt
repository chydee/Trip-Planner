package com.chidi.voyatektripplanner.ui.features.modals

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.rememberAsyncImagePainter
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.data.models.Address
import com.chidi.voyatektripplanner.data.models.Location
import com.chidi.voyatektripplanner.data.models.Response
import com.chidi.voyatektripplanner.ui.components.CenterText
import com.chidi.voyatektripplanner.ui.components.OutlineEditBox
import com.chidi.voyatektripplanner.ui.components.TopTitleBar
import com.chidi.voyatektripplanner.ui.features.landing.LandingPageViewModel
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationModal(
    viewModel: LandingPageViewModel = hiltViewModel(),
    onClose: () -> Unit,
    onSelected: (Location) -> Unit
) {
    val locations = viewModel.locations.collectAsState(initial = Response.loading())

    ModalBottomSheet(
        onDismissRequest = onClose,
        dragHandle = {},
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopTitleBar(
                titleResId = R.string.where,
                onClickAction = onClose
            )
            Text(
                stringResource(R.string.please_select_city),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    top = 16.dp, start = 16.dp, bottom = 10.dp
                )
            )
            OutlineEditBox(
                hintResId = R.string.type_here,
                modifier = Modifier.padding(
                    horizontal = 16.dp
                )
            ) {
                viewModel.getLocations(it)
            }
            locations.value.data.let {
                if (it.isNullOrEmpty()) {
                    CenterText(textResId = R.string.no_results)
                } else
                    LazyColumn {
                        items(
                            items = it,
                            key = { item -> item.place_id }
                        ) {
                            LocationItem(location = it) {
                                onSelected(it)
                            }
                        }
                    }
            }
        }
    }
}

@Composable
fun LocationItem(
    location: Location,
    onSelected: () -> Unit
) {
    var imageLoaded by rememberSaveable {
        mutableStateOf(false)
    }
    val imagePainter = rememberAsyncImagePainter(
        model = "https://flagcdn.com/w320/${location.address.country_code.lowercase()}.png",
        onSuccess = {
            imageLoaded = true
        },
        onError = {
            Log.e("Image Error", it.toString())
        },
        placeholder = painterResource(R.drawable.flag_sa),
        error = painterResource(R.drawable.flag_ng)
    )
    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                R.drawable.location_filled
            ),
            contentDescription = null
        )
        Column(
            Modifier
                .weight(1f)
                .padding(
                    horizontal = 12.dp
                )
        ) {
            Text(
                location.display_name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 16.sp
                )
            )
            Text(
                location.name,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = imagePainter,
                contentDescription = null,
                modifier = Modifier.size(28.dp)
            )
            Text(
                location.address.country_code.uppercase(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@Composable
@Preview
fun LocationSelectorPreview() {
    VoyatekTripPlannerTheme() {
        Scaffold {
            Column {
                LocationModal(
                    onClose = { }
                ) {}
                LocationItem(
                    location = Location(
                        place_id = 1L,
                        name = "Bahama",
                        display_name = "New York, United, States of America",
                        address = Address(
                            state = null,
                            country = "USA",
                            country_code = "NG"
                        )
                    )
                ) {}
            }
        }
    }
}