package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.dungle.android.aiactivassignment.domain.model.Flight
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlightResponse(
    @SerializedName("auto_update")
    val autoUpdate: Boolean,
    @SerializedName("capsules")
    val capsules: List<String>,
    @SerializedName("cores")
    val cores: List<CoreResponse>,
    @SerializedName("crew")
    val crew: List<String>,
    @SerializedName("date_local")
    val dateLocal: String,
    @SerializedName("date_precision")
    val datePrecision: String,
    @SerializedName("date_unix")
    val dateUnix: Int,
    @SerializedName("date_utc")
    val dateUtc: String,
    @SerializedName("details")
    val details: String?,
    @SerializedName("failures")
    val failures: List<String>,
    @SerializedName("fairings")
    val fairings: FairingsResponse?,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("launch_library_id")
    val launchLibraryId: String?,
    @SerializedName("launchpad")
    val launchpad: String,
    @SerializedName("links")
    val links: LinksResponse,
    @SerializedName("name")
    val name: String,
    @SerializedName("net")
    val net: Boolean,
    @SerializedName("payloads")
    val payloads: List<String>,
    @SerializedName("rocket")
    val rocket: String,
    @SerializedName("ships")
    val ships: List<String>,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: String?,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: String?,
    @SerializedName("success")
    val success: String?,
    @SerializedName("tbd")
    val tbd: Boolean,
    @SerializedName("upcoming")
    val upcoming: Boolean,
    @SerializedName("window")
    val window: String?
) : Parcelable {
    fun toFlight(): Flight {
        return Flight(
            id = id,
            dateUtc = dateUtc,
            flightNumber = flightNumber,
            name = name
        )
    }
}
