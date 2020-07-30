package anetos.software.byjuszyoin.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import anetos.software.byjuszyoin.data.db.typeconverter.SourceTypeConverter
import anetos.software.byjuszyoin.data.db.typeconverter.StringTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Top-HeadLines Model
 *
 * created by Jaydeep Bhayani on 30/07/2020
 */

@Parcelize
data class TopHeadLinesResponse(
    @Expose
    @SerializedName("status")
    var status: String? = null,
    @Expose
    @SerializedName("totalResults")
    var totalResults: Int? = null,
    @Expose
    @SerializedName("articles")
    var articles: List<Articles>,
    /*
    * error
    * */
    @Expose
    @SerializedName("code")
    var code: String? = null,
    @Expose
    @SerializedName("message")
    var message: String? = null


) : Parcelable

@Entity
@Parcelize
data class Articles(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @SerializedName("source")
    @Expose
    @ColumnInfo(name = "source")
    @TypeConverters(SourceTypeConverter::class)
    var source: Source? = null,

    @SerializedName("author")
    @Expose
    @ColumnInfo(name = "author")
    var author: String? = null,

    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    var title: String? = null,

    @SerializedName("description")
    @Expose
    @ColumnInfo(name = "description")
    var description: String? = null,

    @SerializedName("url")
    @Expose
    @ColumnInfo(name = "url")
    var url: String? = null,

    @SerializedName("urlToImage")
    @Expose
    @ColumnInfo(name = "urlToImage")
    var urlToImage: String? = null,

    @SerializedName("publishedAt")
    @Expose
    @ColumnInfo(name = "publishedAt")
    var publishedAt: String? = null,

    @SerializedName("content")
    @Expose
    @ColumnInfo(name = "content")
    var content: String? = null
) : Parcelable


@Parcelize
data class Source(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null
) : Parcelable