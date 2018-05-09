package br.com.claro.movies.common

import br.com.claro.movies.dto.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *Created by Anderson on 08/12/2017.
 */
interface ClaroService {
    @GET("movie/popular?api_key=15595205b6a5afdc3bdbe44a00302ba9")
    fun getMovies(@Query("page") page: Int): Single<List<Movie>>

    @GET("movie/{id}")
    fun getMovie(@Path("id") id: String): Single<Movie>
}