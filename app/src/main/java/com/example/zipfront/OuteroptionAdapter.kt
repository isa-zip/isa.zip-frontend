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
import com.example.zipfront.connection.RetrofitClient2
import com.google.gson.Gson

class OuteroptionAdapter(private val outerItemList: List<RetrofitClient2.UserMatchitemData>) : RecyclerView.Adapter<OuteroptionAdapter.ViewHolder>() {

    private val sortedList = outerItemList.sortedBy { it.userRequestInfo.userItemId }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchingoption_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val outerItem = sortedList[position]
        holder.bind(outerItem)
    }

    override fun getItemCount(): Int {
        return sortedList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.pose_rv)
        private val layout1: ConstraintLayout = itemView.findViewById(R.id.layout1)
        private val layout3: ConstraintLayout = itemView.findViewById(R.id.layout3)
        private val textView: TextView = itemView.findViewById(R.id.titletext4)
        private val textView2: TextView = itemView.findViewById(R.id.textView22)
        private val titletext5: TextView = itemView.findViewById(R.id.titletext5)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView2)
        private val imageView2: ImageView = itemView.findViewById(R.id.imageView16)

        fun bind(outerItem: RetrofitClient2.UserMatchitemData) {
            if (outerItem.matchingCount==0) {
                // 내부 아이템이 없는 경우
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
                textView2.text = "${adapterPosition + 1} ${outerItem.dongRequestName}"
                imageView2.setOnClickListener {
                    val intent = Intent(itemView.context, MatchingSecondOptionActivity::class.java)
                    intent.putExtra("title", outerItem.dongRequestName)
                    intent.putExtra("position", adapterPosition)
                    val gson = Gson()
                    val json = gson.toJson(outerItem)
                    intent.putExtra("outerItemJson", json)
                    itemView.context.startActivity(intent)
                    Log.d("MatchingSecondOption", "no inner")
                }
            } else {
                // 내부 아이템이 있는 경우
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE

                // 여기서 outerItem의 내부 아이템 리스트를 사용하여 InnerAdapter 생성 및 설정
                val innerAdapter = InneroptionAdapter(outerItem.matchedBrokerItemResponses)
                innerRecyclerView.adapter = innerAdapter

                // titletext5에 아이템 개수 표시
                titletext5.text = "${outerItem.matchingCount}개"
                textView.text = "${adapterPosition + 1} ${outerItem.dongRequestName}"

                imageView.setOnClickListener {
                    val intent = Intent(itemView.context, MatchingSecondOptionActivity::class.java)
                    intent.putExtra("title", outerItem.dongRequestName)
                    intent.putExtra("position", adapterPosition)
                    val gson = Gson()
                    val json = gson.toJson(outerItem)
                    intent.putExtra("outerItemJson", json)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }
}