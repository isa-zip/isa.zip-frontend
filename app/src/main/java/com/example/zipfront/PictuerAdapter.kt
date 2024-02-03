package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PictuerAdapter: RecyclerView.Adapter<PictuerAdapter.ImageViewHolder>() {
    private val imageList = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_additionalpic, parent, false)
        return ImageViewHolder(view)
    }

    fun addImage(imageUri: Uri) {
        imageList.add(imageUri)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = imageList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView9)

        fun bind(imageUri: Uri) {
            // 이미지를 Glide 등을 사용하여 imageView에 표시
            Glide.with(itemView.context)
                .load(imageUri)
                .into(imageView)
        }
    }
}