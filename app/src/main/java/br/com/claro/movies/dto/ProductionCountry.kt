package br.com.claro.movies.dto

import com.google.gson.annotations.SerializedName

data class ProductionCountry(
        @SerializedName("iso_3166_1") val iso31661: String,
        @SerializedName("name") val name: String
)