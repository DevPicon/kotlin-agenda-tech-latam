package pe.devpicon.android.agendatechlatam.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import pe.devpicon.android.agendatechlatam.R

/**
 * Created by armando on 3/17/17.
 */

infix fun ImageView.loadImage(url: String) {
    Glide.with(this.context).load(url).error(R.drawable.default_image).fitCenter().into(this)
}

fun hola(nombre:String) = "Hola $nombre"