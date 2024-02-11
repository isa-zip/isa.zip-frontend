package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class OuteruploadAdapter(private val outerItemList: List<NewMatchinguploadActivity.OuterUploadItem>) : RecyclerView.Adapter<OuteruploadAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchingupload_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val outerItem = outerItemList[position]
        holder.bind(outerItem, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return outerItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView22)
        private val textView2: TextView = itemView.findViewById(R.id.textView23)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView16)

        fun bind(outerItem: NewMatchinguploadActivity.OuterUploadItem, context: Context) {
            textView.text = outerItem.title
            textView2.text = "${outerItem.dongCount}개" // dongCount를 사용하여 아이템 개수 표시

            imageView.setOnClickListener {
                val intent = Intent(context, MatchingSecondUploadActivity::class.java)
                intent.putExtra("title", outerItem.title)
                intent.putExtra("dongCount", outerItem.dongCount) // dongCount도 함께 전달
                context.startActivity(intent)
            }
        }
    }
}