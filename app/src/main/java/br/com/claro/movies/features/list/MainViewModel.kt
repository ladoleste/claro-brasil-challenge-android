package br.com.claro.movies.features.list

import android.arch.lifecycle.MutableLiveData
import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.BaseViewModel
import br.com.claro.movies.repository.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class MainViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: MoviesRepository

    val movies = MutableLiveData<List<Movie>>()
    val moviesError = MutableLiveData<Throwable>()

    init {
        ClaroApplication.component.inject(this)
    }

    fun loadMovies(page: Int = 1) {
        cDispose.add(repo.getMovies(page)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> Timber.e(t) })
                .subscribe({
                    movies.postValue(it.movies)
                }, {
                    moviesError.postValue(it)
                }))
    }
}
