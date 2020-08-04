package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import anetos.software.byjuszyoin.data.model.Articles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * * [ArticlesTypeConverter]
 * Type Converter to convert [ArticlesTypeConverter] type of data to insert in table in [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */
object ArticlesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun storedStringToArticles(data: String?): List<Articles> {
        if (data == null) {
            return emptyList()
        }
        val listType =
            object : TypeToken<List<Articles?>?>() {}.type
        return gson.fromJson(
            data,
            listType
        )
    }

    @TypeConverter
    fun articlesToStoredString(myObjects: List<Articles?>?): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}