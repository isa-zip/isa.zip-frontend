package com.example.zipfront

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InnerCompleteAdapter (private val itemList: List<String>) : RecyclerView.Adapter<InnerCompleteAdapter.ViewHolder>() {

    // onCreateViewHolder 메서드에서 레이아웃 관리자 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoptiondoublecomplete, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        Log.d("InnerCompleteAdapter", "$item")
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)

        fun bind(item: String) {
            textView.text = item
        }
    }
}