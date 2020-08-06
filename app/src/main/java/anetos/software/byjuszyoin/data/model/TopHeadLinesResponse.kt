package anetos.software.byjuszyoin.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import anetos.software.byjuszyoin.data.db.typeconverter.SourceTypeConverter
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * * [TopHeadLinesResponse]
 *
 * is model class for Top Headlines.
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

@Parcelize
data class TopHeadLinesResponse(
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("totalResults")
    var totalResults: Int? = null,
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
class Articles() : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @SerializedName("source")
    @Expose
    @TypeConverters(SourceTypeConverter::class)
    var source: Source? = null

    @SerializedName("author")
    @Expose
    var author: String? = null

    @SerializedName("title")
    @Expose
    var title: String? = null

    @SerializedName("description")
    @Expose
    var description: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

    @SerializedName("urlToImage")
    @Expose
    var urlToImage: String? = null

    @SerializedName("publishedAt")
    @Expose
    var publishedAt: String? = null

    @SerializedName("content")
    @Expose
    var content: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        source = parcel.readParcelable(Source::class.java.classLoader)
        author = parcel.readString()
        title = parcel.readString()
        description = parcel.readString()
        url = parcel.readString()
        urlToImage = parcel.readString()
        publishedAt = parcel.readString()
        content = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Articles> {
        override fun createFromParcel(parcel: Parcel): Articles {
            return Articles(parcel)
        }

        override fun newArray(size: Int): Array<Articles?> {
            return arrayOfNulls(size)
        }
    }
}

/*@Entity
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
) : Parcelable*/


@Parcelize
data class Source(
    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null
) : Parcelable