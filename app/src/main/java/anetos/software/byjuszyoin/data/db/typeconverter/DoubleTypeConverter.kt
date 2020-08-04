package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * * [DoubleTypeConverter]
 * Type Converter to convert [DoubleTypeConverter] type of data to insert in table in [RoomDatabse]
 * @author
 * created by Jaydeep Bhayani on 30/07/2020
 */

object DoubleTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<Double> {
        val listType =
            object : TypeToken<List<Double?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<Double?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}