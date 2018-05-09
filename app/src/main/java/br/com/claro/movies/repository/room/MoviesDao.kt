package br.com.claro.movies.repository.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import br.com.claro.movies.dto.Movie

/**
 * Created by Anderson on 29/03/2018.
 */
@Dao
interface MoviesDao {
    @Query("SELECT * FROM movie")
    fun loadFavoriteMovies(): LiveData<List<Movie>>

    @Query("SELECT EXISTS(SELECT 1 FROM movie WHERE id = :id LIMIT 1)")
    fun isFavorite(id: Int): Boolean

    @Insert(onConflict = REPLACE)
    fun insert(movie: Movie)

    @Delete
    fun delete(movie: Movie)
}