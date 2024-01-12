package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OuterSecondoptionAdapter(private val innerItems: List<String>) : RecyclerView.Adapter<OuterSecondoptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchingselectactivity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = innerItems[position]
        holder.bind(innerItem, holder.itemView.context)
    }
    override fun getItemCount(): Int {
        return innerItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.option_rv)
        private val textView: TextView = itemView.findViewById(R.id.textView24)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView18)

        fun bind(innerItem: String, context: Context) {
            // 여기서 innerItem 사용하여 필요한 작업 수행
            textView.text = innerItem
            imageView.setImageResource(R.drawable.maruisgang)
            // 비동기적으로 데이터를 가져오고 RecyclerView를 업데이트
            GlobalScope.launch {
                val innerItemList = withContext(Dispatchers.Default) {
                    // 여기에서 createInnerItemList을 호출하거나, 데이터를 가져오는 작업 수행
                    createInnerItemList(innerItem)
                }

                withContext(Dispatchers.Main) {
                    // UI 업데이트는 Main 스레드에서 수행
                    val innerAdapter = InnerSecondoptionAdapter(innerItemList)
                    innerRecyclerView.layoutManager = LinearLayoutManager(context)
                    innerRecyclerView.adapter = innerAdapter
                }
            }
        }
    }
    private fun createInnerItemList(innerItem: String): List<String> {
        // Your logic to create the list of inner items based on innerItem
        // Replace this with your actual implementation
        return listOf("아이템 1", "아이템 2")
    }
}