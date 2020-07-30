package anetos.software.byjuszyoin.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.w3c.dom.Comment

class Converters {

    @TypeConverter
    fun fromGroupTaskMemberList(value: List<Comment>): String {
        val gson = Gson()

        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList(value: String): List<Comment> {
        val gson = Gson()
        val type = object : TypeToken<List<Comment>>() {}.type
        return gson.fromJson(value, type)
    }
}

