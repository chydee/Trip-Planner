package com.chidi.voyatektripplanner.data.models

import androidx.annotation.StringRes
import com.chidi.voyatektripplanner.R
import java.util.Date

data class Trip(
    var id: Int,
    var title: String? = null,
    var style: String? = null,
    var description: String? = null,
    var startDate: Date? = null,
    var endDate: Date? = null,
    var location: Location? = null
) {
    var travelStyle = style?.let { TravelStyle.valueOf(it) }
        set(value) {
            field = value
            style = value?.name
        }

    val canCreate
        get() = isInitSet && title != null && style != null
                && description != null

    val isInitSet get() = startDate != null && endDate != null && location != null

    fun reset() {
        title = null
        style = null
        description = null
        startDate = null
        endDate = null
        location = null
    }
}

data class Location(
    val place_id: Long,
    val name: String,
    val display_name: String,
    val address: Address
)

data class Address(
    val state: String?,
    val country: String,
    val country_code: String
)

enum class TravelStyle(@StringRes val textResId: Int) {
    Solo(R.string.solo), Couple(R.string.couple),
    Family(R.string.family), Group(R.string.group)
}
