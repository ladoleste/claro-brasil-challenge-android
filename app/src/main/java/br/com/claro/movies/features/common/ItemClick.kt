package br.com.claro.movies.features.common

import br.com.claro.movies.dto.Movie

/**
 *Created by Anderson on 15/02/2018.
 */
interface ItemClick {
    fun onItemClick(movie: Movie)
}