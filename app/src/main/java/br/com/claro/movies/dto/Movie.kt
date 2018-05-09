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
        @SerializedName("id") var id: String = "",
        @SerializedName("description") var description: String? = ""
) {
    val title get(): String = if (description.isNullOrBlank()) "" else description!!
    val hasTitle get() = title.isNotBlank()
    val isFavorite get() = false
}