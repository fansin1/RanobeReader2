package org.fansin.ranobereader.domain.model

import android.os.Parcelable
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Images(
    @get: JsonProperty("vertical") val images: List<Image>
) : Parcelable