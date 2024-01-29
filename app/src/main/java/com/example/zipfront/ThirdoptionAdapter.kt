package com.example.zipfront

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ThirdoptionAdapter(private val itemList: MutableList<String> = mutableListOf()) : RecyclerView.Adapter<ThirdoptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingoptiondoublemin, parent, false)
        return ViewHolder(view)
    }

    fun addItems(items: MutableList<String>) {
        itemList.addAll(items)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        Log.d("ThirdoptionAdapter", "${itemList.size}")
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)

        fun bind(item: String) {
            textView.text = item
            // 여기에서 필요한 작업을 수행하십시오.
        }
    }
}