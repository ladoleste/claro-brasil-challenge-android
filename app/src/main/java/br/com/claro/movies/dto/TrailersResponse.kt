package br.com.claro.movies.dto

import com.google.gson.annotations.SerializedName

data class TrailersResponse(
        @SerializedName("id") val id: Int,
        @SerializedName("results") val trailers: List<Trailer>
)