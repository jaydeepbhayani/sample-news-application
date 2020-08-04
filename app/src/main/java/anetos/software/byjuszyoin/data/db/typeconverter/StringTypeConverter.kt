package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * * [StringTypeConverter]
 * Type Converter to convert [StringTypeConverter] type of data to insert in table in [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

object StringTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<String> {
        val listType =
            object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}