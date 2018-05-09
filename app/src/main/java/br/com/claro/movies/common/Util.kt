package br.com.claro.movies.common

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.content.res.AppCompatResources

/**
 * Created by Anderson on 29/03/2018.
 */
object Util {
    fun getBitmapFromVectorDrawable(@DrawableRes drawableId: Int, context: Context): Bitmap {
        var drawable: Drawable = AppCompatResources.getDrawable(context, drawableId)!!
        drawable = DrawableCompat.wrap(drawable).mutate()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    fun readRawFile(file: Int): String {

        try {
            val res = ClaroApplication.instance.resources
            val ins = res.openRawResource(file)
            val b = ByteArray(ins.available())
            ins.read(b)
            return String(b)
        } catch (_: Exception) {

        }
        return ""
    }
}