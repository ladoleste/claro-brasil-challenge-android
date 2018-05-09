package br.com.claro.movies.dto

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Anderson on 27/03/2018.
 */
@Entity
data class Movie(
        @PrimaryKey
        @SerializedName("id") val id: Int,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("video") val video: Boolean,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("title") val title: String,
        @SerializedName("popularity") val popularity: Double,
        @SerializedName("poster_path") val posterPath: String,
        @SerializedName("original_language") val originalLanguage: String,
        @SerializedName("original_title") val originalTitle: String,
        @SerializedName("genre_ids") val genreIds: List<Int>,
        @SerializedName("backdrop_path") val backdropPath: String,
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("overview") val overview: String,
        @SerializedName("release_date") val releaseDate: String
)