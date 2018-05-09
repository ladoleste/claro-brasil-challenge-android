package br.com.claro.movies.repository

import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.common.ClaroService
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.repository.room.ClaroDatabase
import javax.inject.Inject

/**
 * Created by Anderson on 23/03/2018
 */
class MoviesRepositoryImpl : MoviesRepository {
    @Inject
    lateinit var claroService: ClaroService

    @Inject
    lateinit var db: ClaroDatabase

    init {
        ClaroApplication.component.inject(this)
    }

    override fun getMovies(page: Int) = claroService.getMovies(page)

    override fun getMovie(id: String) = claroService.getMovie(id)

    override fun getFavorites() = db.gistDao().loadFavoriteGists()

    override fun addToFavorite(movie: Movie) {
        db.gistDao().insert(movie)
    }

    override fun removeFromFavorites(movie: Movie) {
        db.gistDao().delete(movie)
    }

    override fun isFavorite(id: String) = db.gistDao().isFavorite(id)
}