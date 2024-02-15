package com.example.zipfront

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.squareup.picasso.RequestCreator

data class ManagementData(
    var img : RequestCreator,
    var info1: String,
    var info2: String,
    var info3: String,
    var info4: String,
    var info5: String,
    val tv: String,
    var brokerItemID: Int
)