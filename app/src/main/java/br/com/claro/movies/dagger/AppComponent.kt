package br.com.claro.movies.dagger

import br.com.claro.movies.MainActivity
import br.com.claro.movies.repository.MoviesRepositoryImpl
import dagger.Component
import javax.inject.Singleton


/**
 * Created by Anderson on 21/03/2018
 */

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {
    //    fun inject(target: MainViewModel)
//    fun inject(target: FavoritesViewModel)
//    fun inject(target: DetailsViewModel)
    fun inject(target: MoviesRepositoryImpl)

    fun inject(target: MainActivity)
}