package org.fansin.ranobereader.domain.model

import android.os.Parcelable
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Image(
    val url: String
) : Parcelable
