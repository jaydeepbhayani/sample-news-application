package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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