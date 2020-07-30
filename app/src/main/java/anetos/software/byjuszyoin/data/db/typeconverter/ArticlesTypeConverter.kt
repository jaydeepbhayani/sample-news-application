package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import anetos.software.byjuszyoin.data.model.Articles
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ArticlesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun storedStringToCurrency(data: String?): List<Articles> {
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
    fun currencyToStoredString(myObjects: List<Articles?>?): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}