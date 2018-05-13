package br.com.claro.movies.features.detail

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.claro.movies.R
import br.com.claro.movies.dto.Trailer
import com.bumptech.glide.Glide


class VideoPagerAdapter(private val videos: List<Trailer>) : PagerAdapter() {

    override fun isViewFromObject(view: View, obj: Any) = view === obj

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val video = videos[position]
        val inflater = LayoutInflater.from(container.context)
        val layout = inflater.inflate(R.layout.item_image, container, false) as ImageView
        Glide.with(container.context).load(video.thumbnail).into(layout)
        container.addView(layout)
        return layout
    }

    override fun getCount() = videos.size
}