package br.com.claro.movies.common

import android.app.Activity
import android.os.Build
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import br.com.claro.movies.R
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun ViewGroup.inflate(layoutId: Int): View = LayoutInflater.from(context).inflate(layoutId, this, false)

fun ImageView.loadImage(imageUrl: String?) {
    if (context is Activity) {
        val act = context as Activity
        if (!act.isFinishing && !act.isDestroyed)
            Glide.with(act).load(imageUrl).apply(RequestOptions().placeholder(drawable)).into(this)
    }
}

fun String.toHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        Html.fromHtml(this)
    }
}

fun Throwable?.getErrorMessage() = when (this) {
    is SocketTimeoutException -> R.string.no_connection
    is UnknownHostException -> R.string.no_connection
    else -> R.string.generic_error
}

inline fun FragmentManager.transaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transaction { add(frameId, fragment) }

}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.transaction { replace(frameId, fragment) }
}