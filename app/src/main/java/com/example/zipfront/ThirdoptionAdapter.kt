package com.example.zipfront

import android.content.Intent
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

        init {
            // 아이템 전체에 대한 클릭 리스너 설정
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZItmeInfoActivity2::class.java)

                itemView.context.startActivity(intent)
            }
        }

        fun bind(item: String) {
            textView.text = item
            // 여기에서 필요한 작업을 수행하십시오.
        }
    }
}