package br.com.claro.movies.features.detail

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide


/**
 * Created by Anderson on 29/03/2018.
 */
object DataBinder {
    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setImageUrl(imageView: ImageView, url: String?) {
        Glide.with(imageView.context).load(url).into(imageView)
    }

    @JvmStatic
    @BindingAdapter("android:visibility")
    fun setVisibility(view: View, b: Boolean) {
        view.visibility = if (b) View.VISIBLE else View.GONE
    }
}