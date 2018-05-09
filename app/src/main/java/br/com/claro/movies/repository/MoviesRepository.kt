package br.com.claro.movies.repository

import android.arch.lifecycle.LiveData
import br.com.claro.movies.dto.Movie
import io.reactivex.Single

/**
 * Created by Anderson on 23/03/2018
 */
interface MoviesRepository {
    fun getMovies(page: Int): Single<List<Movie>>
    fun getMovie(id: String): Single<Movie>
    fun getFavorites(): LiveData<List<Movie>>
    fun addToFavorite(movie: Movie)
    fun removeFromFavorites(movie: Movie)
    fun isFavorite(id: String): Boolean
}