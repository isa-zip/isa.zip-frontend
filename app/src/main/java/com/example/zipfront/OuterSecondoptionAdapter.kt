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

class OuterSecondoptionAdapter(private val innerItems: List<RetrofitClient2.MatchedBrokerItemResponse>,
                               private val userItemId: Int) : RecyclerView.Adapter<OuterSecondoptionAdapter.ViewHolder>() {

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
            val distinctInnerItems = innerItems.distinctBy { it.businessName }

            // businessName이 현재 아이템의 이름과 같은지 확인합니다.
            val isSameBusinessName = innerItems.any { it.businessName == innerItem.businessName }
            // 중복되는지 여부와 관계없이 기본적으로 아이템의 정보를 표시합니다.
            Glide.with(itemView)
                .load(innerItem.detailResponse.itemImages.firstOrNull()?.imageUrl)
                .into(imageView)

            val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL, false)
            innerRecyclerView.layoutManager = layoutManager

            // 중복되는 경우 중에서 "matchingId"가 가장 작은 아이템에 대해서만 텍스트와 이미지를 보여줍니다.
            val minMatchingIdItem = innerItems.filter { it.businessName == innerItem.businessName }.minByOrNull { it.matchingId }
            if (minMatchingIdItem?.matchingId == innerItem.matchingId) {
                textView.text = innerItem.businessName
                textView.visibility = View.VISIBLE
                imageView.visibility = View.VISIBLE

                val innerAdapter = InnerSecondoptionAdapter(listOf(innerItem), userItemId)
                innerRecyclerView.adapter = innerAdapter
            }
            else
            {
                textView.visibility = View.GONE
                imageView.visibility = View.GONE

                val innerAdapter = InnerSecondoptionAdapter(listOf(innerItem), userItemId)
                innerRecyclerView.adapter = innerAdapter
            }
        }
    }
}