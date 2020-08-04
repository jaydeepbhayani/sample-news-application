package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import anetos.software.byjuszyoin.data.model.Articles
import anetos.software.byjuszyoin.data.model.Source
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * * [SourceTypeConverter]
 * Type Converter to convert [SourceTypeConverter] type of data to insert in table in [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */


object SourceTypeConverter {
    var gson = Gson()

    @JvmStatic
    @TypeConverter
    fun storedStringToTranslations(data: String?): Source {
        if (data == null) {
            return emptyList<Any>() as Source
        }
        val listType = object : TypeToken<Source?>() {}.type
        return gson.fromJson(data, listType)
    }

    @JvmStatic
    @TypeConverter
    fun sourceToStoredString(myObjects: Source?): String {
        val gson = Gson()
        return gson.toJson(myObjects)
    }
}