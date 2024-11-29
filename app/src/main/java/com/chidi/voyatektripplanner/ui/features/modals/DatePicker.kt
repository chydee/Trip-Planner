package com.chidi.voyatektripplanner.ui.features.modals

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chidi.voyatektripplanner.R
import com.chidi.voyatektripplanner.ui.components.MainButton
import com.chidi.voyatektripplanner.ui.components.TopTitleBar
import com.chidi.voyatektripplanner.ui.theme.LocalCustomColors
import com.chidi.voyatektripplanner.ui.theme.VoyatekTripPlannerTheme
import com.chidi.voyatektripplanner.ui.utils.format
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker(
    onClose: () -> Unit,
    onSelected: (Pair<Date?, Date?>) -> Unit
) {
    val selectedDates = remember {
        mutableStateOf(Pair<Date?, Date?>(null, null))
    }

    val currentMonth = remember { YearMonth.now() }
    val state = rememberCalendarState(
        startMonth = remember { currentMonth.minusMonths(100) },
        endMonth = remember { currentMonth.plusMonths(100) },
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = remember { firstDayOfWeekFromLocale() }
    )

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
                titleResId = R.string.date,
                onClickAction = onClose
            )
            Box(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                VerticalCalendar(
                    state = state,
                    dayContent = { day ->
                        val date = Date.from(day.date.atStartOfDay(ZoneId.systemDefault()).toInstant())
                        Day(
                            day = day,
                            isSelected =
                            date == selectedDates.value.first ||
                                    date == selectedDates.value.second ||
                                    (selectedDates.value.first != null &&
                                            selectedDates.value.second != null &&
                                            date.after(selectedDates.value.first) &&
                                            date.before(selectedDates.value.second))
                        ) {
                            selectedDates.value =
                                if (selectedDates.value.first == null) {
                                    if (selectedDates.value.second == null)
                                        Pair(date, null)
                                    else if (date.before(selectedDates.value.second))
                                        selectedDates.value.copy(first = date)
                                    else
                                        Pair(selectedDates.value.second, date)
                                } else if (selectedDates.value.second == null) {
                                    selectedDates.value.copy(second = date)
                                } else if (selectedDates.value.first == date) {
                                    selectedDates.value.copy(first = null)
                                } else if (selectedDates.value.second == date) {
                                    selectedDates.value.copy(second = null)
                                } else if (date.before(selectedDates.value.first)) {
                                    selectedDates.value.copy(first = date)
                                } else {
                                    selectedDates.value.copy(second = date)
                                }
                        }
                    },
                    monthHeader = { month ->
                        Text(
                            month.yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyy")),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 14.dp, top = 24.dp)
                        )
                        MonthHeader(
                            daysOfWeek = month.weekDays.first().map { it.date.dayOfWeek }
                        )
                    }
                )
                Column(
                    Modifier
                        .align(Alignment.BottomCenter)
                        .background(LocalCustomColors.current.white)
                        .padding(vertical = 12.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row(Modifier.padding(bottom = 3.dp)) {
                        Text(
                            stringResource(R.string.start_date),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            stringResource(R.string.end_date),
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        )
                    }
                    Row(
                        Modifier.padding(bottom = 16.dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        SelectedDateBox(
                            date = selectedDates.value.first,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        SelectedDateBox(
                            date = selectedDates.value.second,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    MainButton(
                        buttonTextResId = R.string.choose_date
                    ) {
                        onSelected(selectedDates.value)
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedDateBox(
    date: Date?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 12.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = date?.format("EEE, MMM d") ?: "",
            style = MaterialTheme.typography.titleSmall
        )
        Icon(
            painter = painterResource(R.drawable.calender_grey),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = dayOfWeek.name.take(2).lowercase()
                    .replaceFirstChar {
                        if (it.isLowerCase())
                            it.titlecase(Locale.ROOT)
                        else it.toString()
                    },
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: (CalendarDay) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(4.dp)
            .background(
                color = if (isSelected && day.position == DayPosition.MonthDate)
                    LocalCustomColors.current.blue60
                else LocalCustomColors.current.white60,
                shape = RoundedCornerShape(3.dp)
            )
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            fontWeight = FontWeight.Medium,
            color = if (day.position != DayPosition.MonthDate)
                Color.LightGray
            else if (isSelected) LocalCustomColors.current.white
            else Color.Unspecified
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
@Preview
fun CalenderLayPreview() {
    VoyatekTripPlannerTheme() {
        Scaffold {
            Column {
                DatePicker(
                    onClose = { /*TODO: Handle onClose here*/ }
                ) {}
            }
        }
    }
}