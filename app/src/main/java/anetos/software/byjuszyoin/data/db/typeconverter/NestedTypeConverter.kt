package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * * [NestedTypeConverter]
 * Type Converter to convert [NestedTypeConverter] or [Any] type of data to insert in table in [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

object NestedTypeConverter {
    @TypeConverter
    fun fromObject(value: String?): List<Any> {
        val listType =
            object : TypeToken<List<Any?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Any?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}