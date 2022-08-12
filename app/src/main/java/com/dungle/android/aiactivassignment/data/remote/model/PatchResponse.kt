package com.dungle.android.aiactivassignment.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatchResponse(
    @SerializedName("large")
    val large: String?,
    @SerializedName("small")
    val small: String?
) : Parcelable