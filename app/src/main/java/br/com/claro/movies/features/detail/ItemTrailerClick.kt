package br.com.claro.movies.features.detail

import br.com.claro.movies.dto.Trailer

/**
 *Created by Anderson on 15/02/2018.
 */
interface ItemTrailerClick {
    fun onItemClick(movie: Trailer)
}