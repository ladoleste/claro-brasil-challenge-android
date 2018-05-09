package br.com.claro.movies.features.favorites

import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.features.common.BaseViewModel
import br.com.claro.movies.repository.MoviesRepository
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class FavoritesViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: MoviesRepository

    init {
        ClaroApplication.component.inject(this)
    }

    fun loadFavorites() = repo.getFavorites()
}
