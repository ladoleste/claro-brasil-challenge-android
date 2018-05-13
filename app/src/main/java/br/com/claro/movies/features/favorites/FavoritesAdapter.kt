package br.com.claro.movies.features.favorites

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.claro.movies.common.loadImage
import br.com.claro.movies.databinding.ItemMovieBinding
import br.com.claro.movies.dto.Movie
import br.com.claro.movies.features.common.ItemClick
import kotlinx.android.synthetic.main.item_movie.view.*


class FavoritesAdapter(_items: List<Movie>, private val itemClick: ItemClick) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    private val items = mutableListOf<Movie>()

    init {
        items.addAll(_items)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = items[position]
        holder.itemView.setOnClickListener { itemClick.onItemClick(movie, holder.itemView.iv_avatar) }
        holder.bind(movie)
    }

    class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Movie) {
            binding.movie = item
            binding.ivAvatar.loadImage(item.posterUrl)
            binding.executePendingBindings()
        }
    }

    fun updateItems(it: List<Movie>) {
        items.clear()
        items.addAll(it)
        notifyDataSetChanged()
    }
}