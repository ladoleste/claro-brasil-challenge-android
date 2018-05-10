package br.com.claro.movies.service

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
interface MoviesService {
    @GET("movie/popular?api_key=15595205b6a5afdc3bdbe44a00302ba9")
    fun getMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{id}?api_key=15595205b6a5afdc3bdbe44a00302ba9")
    fun getMovie(@Path("id") id: Int): Single<MovieDetails>

    @GET("movie/{id}/videos?api_key=15595205b6a5afdc3bdbe44a00302ba9")
    fun getTrailers(@Path("id") id: Int): Single<TrailersResponse>
}