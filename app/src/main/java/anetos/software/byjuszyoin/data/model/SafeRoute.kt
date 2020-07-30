package anetos.software.byjuszyoin.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Top-HeadLines Model
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */

@Entity
@Parcelize
data class SafeRoute(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("routeId")
    var id: Int = 0,
    @SerializedName("start")
    var start: String = "",
    @SerializedName("destination")
    var destination: String = ""
) : Parcelable