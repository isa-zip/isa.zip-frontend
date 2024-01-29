package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InnerSecondoptionAdapter(private val itemList: List<String>, private val onImageViewClick: (String) -> Unit
) : RecyclerView.Adapter<InnerSecondoptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoptiondoubleact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = itemList[position]
        holder.bind(innerItem)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)
        private val imageView22: ImageView = itemView.findViewById(R.id.imageView22)


        fun bind(innerItem: String) {
            // You can use innerItem as needed
            // For example, you can set the text of the textView with the innerItem
            textView.text = innerItem

            // 이미지 뷰 클릭 이벤트 처리
            imageView22.setOnClickListener {
                // 클릭된 아이템의 값을 전달
                onImageViewClick.invoke(innerItem)
                // 이미지 변경
                imageView22.setImageResource(R.drawable.ic_plusbox)
            }
        }
    }
}