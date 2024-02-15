package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OuterSecondoptionAdapter(private val innerItems: List<RetrofitClient2.MatchedBrokerItemResponse>) : RecyclerView.Adapter<OuterSecondoptionAdapter.ViewHolder>() {

    private var thirdAdapter: ThirdoptionAdapter? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchingselectactivity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = innerItems[position]
        holder.bind(innerItem)
    }

    override fun getItemCount(): Int {
        return innerItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.option_rv)
        private val textView: TextView = itemView.findViewById(R.id.textView24)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView18)

        fun bind(innerItem: RetrofitClient2.MatchedBrokerItemResponse) {
            // 여기서 innerItem 사용하여 필요한 작업 수행
            Log.d("Retrofit9", "$innerItem")
            textView.text = innerItem.businessName
            Glide.with(itemView)
                .load(innerItem.detailResponse.itemImages.firstOrNull()?.imageUrl) // 첫 번째 이미지 URL을 사용합니다.
                .into(imageView)

            val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            innerRecyclerView.layoutManager = layoutManager
            Log.d("Retrofit93", listOf(innerItem).toString())
            val innerAdapter = InnerSecondoptionAdapter(listOf(innerItem))
            innerRecyclerView.adapter = innerAdapter
        }
    }
}