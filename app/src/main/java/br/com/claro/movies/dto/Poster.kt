package br.com.claro.movies.dto

import br.com.claro.movies.BuildConfig
import com.google.gson.annotations.SerializedName

data class Poster(
        @SerializedName("aspect_ratio") val aspectRatio: Double,
        @SerializedName("file_path") val filePath: String,
        @SerializedName("height") val height: Int,
        @SerializedName("iso_639_1") val iso6391: String,
        @SerializedName("vote_average") val voteAverage: Double,
        @SerializedName("vote_count") val voteCount: Int,
        @SerializedName("width") val width: Int
) {
    val fileUrl get() = BuildConfig.BASE_IMAGE_PATH + filePath
}