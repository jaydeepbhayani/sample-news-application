package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SourceTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun storedStringToSource(data: String?): Source? {
        if (data == null) {
            return null
        }
        val listType =
            object : TypeToken<Source?>() {}.type
        return gson.fromJson(
            data,
            listType
        )
    }

    @TypeConverter
    fun sourceToStoredString(myObjects: Source?): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}