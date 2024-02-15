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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.connection.RetrofitClient2
import com.google.gson.Gson

class OuterCompleteAdapter(private val outerItemList: List<RetrofitClient2.UserMatchitemData>) :
    RecyclerView.Adapter<OuterCompleteAdapter.ViewHolder>() {

    private val sortedList = outerItemList.sortedBy { it.userRequestInfo.userItemId }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingcomplete_layout, parent, false)
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
        private val textView3: TextView = itemView.findViewById(R.id.textView23)
        private val titletext5: TextView = itemView.findViewById(R.id.titletext5)
        private val imageView16: ImageView = itemView.findViewById(R.id.imageView16)
        private val imageView2: ImageView = itemView.findViewById(R.id.imageView2)

        init {
            imageView16.setOnClickListener {
                // 이미지 뷰를 클릭하면 layout1과 layout3의 가시성을 전환
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE
            }
            imageView2.setOnClickListener {
                // imageView2를 클릭하면 layout1과 layout3의 가시성을 전환
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
            }
        }

        // 수정된 부분: bind 메서드의 매개변수 타입을 MatchingCompleteFragment.OuterItem2로 변경
        fun bind(outerItem: RetrofitClient2.UserMatchitemData) {
            if (outerItem.matchingCount==0) {
                // 내부 아이템이 없는 경우
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
                // titletext5에 아이템 개수 표시
                titletext5.text = "${outerItem.matchingCount}개"
                textView.text = "${outerItem.userRequestInfo.userItemId} ${outerItem.dongRequestName}"
                textView2.text = "${outerItem.userRequestInfo.userItemId} ${outerItem.dongRequestName}"
            } else {
                // 내부 아이템이 있는 경우
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE

                val layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                innerRecyclerView.layoutManager = layoutManager
                // 여기서 outerItem의 내부 아이템 리스트를 사용하여 InnerAdapter 생성 및 설정
                val innerAdapter = InnerCompleteAdapter(outerItem.matchedBrokerItemResponses)
                innerRecyclerView.adapter = innerAdapter

                // titletext5에 아이템 개수 표시
                titletext5.text = "${outerItem.matchingCount}개"
                textView.text = "${outerItem.userRequestInfo.userItemId} ${outerItem.dongRequestName}"
                textView2.text = "${outerItem.userRequestInfo.userItemId} ${outerItem.dongRequestName}"
            }
        }
    }
}