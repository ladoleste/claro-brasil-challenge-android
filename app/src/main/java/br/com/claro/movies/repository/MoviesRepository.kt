package br.com.claro.movies.repository

import android.arch.lifecycle.LiveData
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.dto.MovieDetails
import br.com.claro.movies.dto.MovieResponse
import br.com.claro.movies.dto.TrailersResponse
import io.reactivex.Single

/**
 * Created by Anderson on 23/03/2018
 */
interface MoviesRepository {
    fun getMovies(page: Int): Single<MovieResponse>
    fun getTrailers(id: Int): Single<TrailersResponse>
    fun getMovie(id: Int): Single<MovieDetails>
    fun getFavorites(): LiveData<List<Movie>>
    fun addToFavorite(movie: Movie)
    fun removeFromFavorites(movie: Movie)
    fun isFavorite(id: Int): Boolean
}