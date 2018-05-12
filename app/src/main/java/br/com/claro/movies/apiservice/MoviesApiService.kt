package br.com.claro.movies.apiservice

import br.com.claro.movies.dto.ImagesResponse
import br.com.claro.movies.dto.MovieDetails
import br.com.claro.movies.dto.MovieResponse
import br.com.claro.movies.dto.TrailersResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by Anderson on 08/12/2017.
 */
interface MoviesApiService {
    @GET("movie/popular")
    fun getMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: Int): Single<MovieDetails>

    @GET("movie/{id}/videos")
    fun getTrailers(@Path("id") id: Int): Single<TrailersResponse>

    @GET("movie/{id}/images")
    fun getImages(@Path("id") id: Int): Single<ImagesResponse>

    @GET("search/movie")
    fun getSuggestions(@Query("query") query: String): Single<MovieResponse>
}