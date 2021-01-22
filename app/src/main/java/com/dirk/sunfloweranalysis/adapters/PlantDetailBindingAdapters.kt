package com.dirk.sunfloweranalysis.adapters

import android.text.Html.FROM_HTML_MODE_COMPACT
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dirk.sunfloweranalysis.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

// 为ImageView增加一个 imageFromUrl属性，属性的形参 imageUrl

@BindingAdapter("imageFromUrl")
fun bindImageUrl(view:ImageView, imageUrl:String){
    if(!imageUrl.isNullOrEmpty()){
        Glide.with(view.context)
            .load(imageUrl)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}


@BindingAdapter("isFabGone")
fun bindIsFabGone(view:FloatingActionButton, isGone:Boolean?){
    if(isGone == null || isGone){
        view.hide()
    } else {
        view.show()
    }
}

@BindingAdapter("renderHtml")
fun bindRenderHtml(view:TextView, description:String?){
    if(description != null){
        view.text = HtmlCompat.fromHtml(description, FROM_HTML_MODE_COMPACT)
        view.movementMethod = LinkMovementMethod.getInstance()
    } else {
        view.text = ""
    }
}


@BindingAdapter("wateringText")
fun bindWateringText(textView: TextView, wateringInterval: Int) {
    val resources = textView.context.resources
    val quantityString = resources.getQuantityString(
        R.plurals.watering_needs_suffix,
        wateringInterval,
        wateringInterval
    )

    textView.text = quantityString
}