package br.com.claro.movies.repository.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


/**
 * Created by Anderson on 29/03/2018.
 */
class ClaroConverters {

    @TypeConverter
    fun fileToString(file: Map<String, File>?): String? = Gson().toJson(file)

    @TypeConverter
    fun stringToFile(str: String?): Map<String, File>? {
        val type = object : TypeToken<Map<String, File>>() {}.type
        return Gson().fromJson<Map<String, File>>(str, type)
    }

//    @TypeConverter
//    fun dateToLong(date: Date) = date.time
//
//    @TypeConverter
//    fun longToDate(time: Long) = Date(time)
}