package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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