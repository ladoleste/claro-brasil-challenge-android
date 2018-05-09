package br.com.claro.movies.repository.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

import br.com.claro.movies.dto.Movie

/**
 * Created by Anderson on 29/03/2018.
 */
@Database(entities = [(Movie::class)], version = 1)
@TypeConverters(ClaroConverters::class)
abstract class ClaroDatabase : RoomDatabase() {
    internal abstract fun movieDao(): MoviesDao
}
