package br.com.claro.movies.dagger

import br.com.claro.movies.features.detail.DetailsViewModel
import br.com.claro.movies.features.favorites.FavoritesViewModel
import br.com.claro.movies.features.list.MainViewModel
import br.com.claro.movies.features.search.SearchViewModel
import br.com.claro.movies.repository.MoviesRepositoryImpl
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Anderson on 21/03/2018
 */

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    fun inject(target: MainViewModel)
    fun inject(target: FavoritesViewModel)
    fun inject(target: SearchViewModel)
    fun inject(target: DetailsViewModel)
    fun inject(target: MoviesRepositoryImpl)
}