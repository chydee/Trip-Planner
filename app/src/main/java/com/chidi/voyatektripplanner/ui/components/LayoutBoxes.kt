package com.chidi.voyatektripplanner.ui.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.data.models.TravelStyle
import com.chidi.voyatektripplanner.data.models.Trip
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme
import java.util.Calendar

@Composable
fun TripViewBox(
    trip: Trip,
    modifier: Modifier = Modifier,
    onClickAction: (Int) -> Unit
) {
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = Color.LightGray
        ),
        colors = CardDefaults.cardColors().copy(
            containerColor = LocalCustomColors.current.white80
        ),
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.padding(top = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(R.drawable.trip_image),
                contentDescription = trip.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                trip.location!!.name,
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(end = 18.dp, top = 18.dp)
                    .background(
                        color = LocalCustomColors.current.blueDark80,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(
                        horizontal = 28.dp,
                        vertical = 14.dp
                    ),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = LocalCustomColors.current.white80
                )
            )
        }
        Text(
            trip.title!!,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(
                start = 16.dp, bottom = 12.dp
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                trip.startDate!!.format(),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                )
            )
            Text(
                "${
                    trip.endDate!!.compareTo(trip.startDate)
                } Days",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        MainButton(
            buttonTextResId = R.string.view,
            modifier = Modifier.padding(16.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            )
        ) {
            onClickAction(trip.id)
        }
    }
}

@Composable
fun CenterText(
    @StringRes textResId: Int
) {
    Text(
        stringResource(textResId),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 16.dp
            )
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun TripViewBoxPreview() {
    VoyatekTripPlannerTheme {
        Scaffold {
            TripViewBox(
                trip = Trip(
                    id = 1,
                    title = "Bahamas Family Trip",
                    location = Location(
                        place_id = 1L,
                        name = "Bahama",
                        display_name = "New York, United, States of America",
                        address = Address(
                            state = null,
                            country = "USA",
                            country_code = "NG"
                        )
                    ),
                    startDate = Calendar.getInstance().time,
                    endDate = Calendar.getInstance().time
                ).apply { travelStyle = TravelStyle.Solo }
            ) {

            }
        }
    }
}