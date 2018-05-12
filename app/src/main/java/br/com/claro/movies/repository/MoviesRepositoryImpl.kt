package br.com.claro.movies.repository

import br.com.claro.movies.apiservice.MoviesApiService
import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.repository.room.ClaroDatabase
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class MoviesRepositoryImpl : MoviesRepository {

    @Inject
    lateinit var moviesApiService: MoviesApiService

    @Inject
    lateinit var db: ClaroDatabase

    init {
        ClaroApplication.component.inject(this)
    }

    override fun getMovies(page: Int) = moviesApiService.getMovies(page)

    override fun getSuggestions(query: String) = moviesApiService.getSuggestions(query)

    override fun getTrailers(id: Int) = moviesApiService.getTrailers(id)

    override fun getMovie(id: Int) = moviesApiService.getMovie(id)

    override fun getFavorites() = db.movieDao().loadFavoriteMovies()

    override fun addToFavorite(movie: Movie) {
        db.movieDao().insert(movie)
    }

    override fun removeFromFavorites(movie: Movie) {
        db.movieDao().delete(movie)
    }

    override fun isFavorite(id: Int) = db.movieDao().isFavorite(id)

}