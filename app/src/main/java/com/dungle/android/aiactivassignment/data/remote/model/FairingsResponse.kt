package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FairingsResponse(
    @SerializedName("recovered")
    val recovered: String?,
    @SerializedName("recovery_attempt")
    val recoveryAttempt: String?,
    @SerializedName("reused")
    val reused: String?,
    @SerializedName("ships")
    val ships: List<String>
) : Parcelable