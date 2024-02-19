package com.example.zipfront

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.RequestCreator

data class PropertyData(
    var img : RequestCreator,
    var info1: String,
    var info2: String,
    var info3: String,
    var info4: String,
    val tv: String,
    var brokerItemID: Int
)
