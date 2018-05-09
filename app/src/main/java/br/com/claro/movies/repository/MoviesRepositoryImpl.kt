package br.com.claro.movies.repository

import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.repository.room.ClaroDatabase
import br.com.claro.movies.service.MoviesService
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class MoviesRepositoryImpl : MoviesRepository {
    @Inject
    lateinit var moviesService: MoviesService

    @Inject
    lateinit var db: ClaroDatabase

    init {
        ClaroApplication.component.inject(this)
    }

    override fun getMovies(page: Int) = moviesService.getMovies(page)

    override fun getMovie(id: Int) = moviesService.getMovie(id)

    override fun getFavorites() = db.movieDao().loadFavoriteMovies()

    override fun addToFavorite(movie: Movie) {
        db.movieDao().insert(movie)
    }

    override fun removeFromFavorites(movie: Movie) {
        db.movieDao().delete(movie)
    }

    override fun isFavorite(id: Int) = db.movieDao().isFavorite(id)
}