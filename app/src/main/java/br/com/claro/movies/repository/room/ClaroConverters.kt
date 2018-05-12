package br.com.claro.movies.repository.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


/**
 * Created by Anderson on 29/03/2018.
 */
class ClaroConverters {

    @TypeConverter
    fun intListToString(list: List<Int>): String? = Gson().toJson(list)

    @TypeConverter
    fun stringToIntlist(str: String?): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson<List<Int>>(str, type)
    }

    @TypeConverter
    fun dateToLong(date: Date) = date.time

    @TypeConverter
    fun longToDate(time: Long) = Date(time)
}