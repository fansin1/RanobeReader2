package org.fansin.ranobereader.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
data class Author(
    @ColumnInfo(name = "author_id")
    val id: Int,
    val name: String
) : Parcelable
