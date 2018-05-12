package br.com.claro.movies.dto

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("backdrops") val backdrops: List<Backdrop>,
        @SerializedName("posters") val posters: List<Poster>
)