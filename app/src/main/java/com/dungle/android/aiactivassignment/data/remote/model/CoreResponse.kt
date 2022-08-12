package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoreResponse(
    @SerializedName("core")
    val core: String?,
    @SerializedName("flight")
    val flight: Int,
    @SerializedName("gridfins")
    val gridfins: Boolean,
    @SerializedName("landing_attempt")
    val landingAttempt: String?,
    @SerializedName("landing_success")
    val landingSuccess: String?,
    @SerializedName("landing_type")
    val landingType: String?,
    @SerializedName("landpad")
    val landpad: String?,
    @SerializedName("legs")
    val legs: Boolean,
    @SerializedName("reused")
    val reused: Boolean
) : Parcelable