package br.com.claro.movies.features.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.claro.movies.common.loadImage
import br.com.claro.movies.databinding.ItemTrailerBinding
import br.com.claro.movies.dto.Trailer


class TrailerAdapter(_items: List<Trailer>, private val itemClick: ItemTrailerClick) : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {

    private val items = mutableListOf<Trailer>()

    init {
        items.addAll(_items)
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrailerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailer = items[position]
        holder.itemView.setOnClickListener { itemClick.onItemClick(trailer) }
        holder.bind(trailer)
    }

    class ViewHolder(private val binding: ItemTrailerBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Trailer) {
            binding.trailer = item
            binding.ivAvatar.loadImage(item.thumbnail)
            binding.executePendingBindings()
        }
    }

    fun updateItems(it: List<Trailer>) {
        items.addAll(it)
        notifyDataSetChanged()
    }
}
