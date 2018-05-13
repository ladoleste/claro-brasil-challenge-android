package br.com.claro.movies.features.detail

import android.arch.lifecycle.MutableLiveData
import br.com.claro.movies.common.ClaroApplication
import br.com.claro.movies.dto.Backdrop
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.dto.MovieDetails
import br.com.claro.movies.dto.Trailer
import br.com.claro.movies.features.common.BaseViewModel
import br.com.claro.movies.repository.MoviesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

/**
 *Created by Anderson on 10/12/2017.
 */
class DetailsViewModel : BaseViewModel() {

    @Inject
    lateinit var repo: MoviesRepository

    val movie = MutableLiveData<MovieDetails>()
    val trailers = MutableLiveData<List<Trailer>>()
    val images = MutableLiveData<List<Backdrop>>()
    val title = MutableLiveData<String>()
    val releaseDate = MutableLiveData<String>()
    val overview = MutableLiveData<String>()
    val posterUrl = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()

    val movieError = MutableLiveData<Throwable>()
    val imagesError = MutableLiveData<Throwable>()
    val trailersError = MutableLiveData<Throwable>()

    init {
        ClaroApplication.component.inject(this)
    }

    fun loadMovie(id: Int) {

        val favorite = repo.isFavorite(id)

        cDispose.add(repo.getMovie(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> Timber.e(t) }).subscribe({
                    movie.postValue(it)
                    title.postValue(it.title)
                    posterUrl.postValue(it.posterUrl)
                    overview.postValue(it.overview)
                    releaseDate.postValue(it.year)
                    isFavorite.postValue(favorite)
                }, {
                    movieError.postValue(it)
                }))
    }

    fun loadTrailers(id: Int) {

        cDispose.add(repo.getTrailers(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> Timber.e(t) }).subscribe({
                    trailers.postValue(it.trailers)
                }, {
                    trailersError.postValue(it)
                }))
    }

    fun loadImages(id: Int) {

        cDispose.add(repo.getImages(id)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError({ t -> Timber.e(t) }).subscribe({
                    images.postValue(it.backdrops)
                }, {
                    imagesError.postValue(it)
                }))
    }

    fun addToFavorites() {
        try {
            repo.addToFavorite(movie.value!!.toMovie())
        } catch (e: Exception) {
            Timber.e(e)
            movieError.postValue(e)
        }
    }

    fun removeFromFavorites() {
        try {
            repo.removeFromFavorites(movie.value!!.toMovie())
        } catch (e: Exception) {
            Timber.e(e)
            movieError.postValue(e)
        }
    }

    private fun MovieDetails.toMovie() = Movie(
            this.id,
            this.voteCount,
            this.video,
            this.voteAverage,
            this.title,
            this.popularity,
            this.posterPath,
            this.originalLanguage,
            this.originalTitle,
            emptyList(),
            this.backdropPath,
            this.adult,
            this.overview,
            this.releaseDate
    )
}