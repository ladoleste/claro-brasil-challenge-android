package br.com.claro.movies.features.search

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
class SearchViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: MoviesRepository

    val movies = MutableLiveData<List<Movie>>()
    val moviesError = MutableLiveData<Throwable>()

    init {
        ClaroApplication.component.inject(this)
    }

    fun loadSuggestions(query: String) {

        if (query.isBlank()) {
            movies.postValue(emptyList())
        } else {
            cDispose.add(repo.getSuggestions(query)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError({ t -> Timber.e(t) })
                    .subscribe({
                        movies.postValue(it.movies)
                    }, {
                        moviesError.postValue(it)
                    }))
        }
    }
}
