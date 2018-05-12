package br.com.claro.movies.repository

import android.arch.lifecycle.LiveData
import br.com.claro.movies.dto.*
import io.reactivex.Single

/**
 * Created by Anderson on 23/03/2018
 */
interface MoviesRepository {
    fun getSuggestions(query: String): Single<MovieResponse>
    fun getMovies(page: Int): Single<MovieResponse>
    fun getTrailers(id: Int): Single<TrailersResponse>
    fun getImages(id: Int): Single<ImagesResponse>
    fun getMovie(id: Int): Single<MovieDetails>
    fun getFavorites(): LiveData<List<Movie>>
    fun addToFavorite(movie: Movie)
    fun removeFromFavorites(movie: Movie)
    fun isFavorite(id: Int): Boolean
}