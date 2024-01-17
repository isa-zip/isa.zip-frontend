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

class OuterCompleteAdapter(private val outerItemList: List<MatchingCompleteFragment.OuterItem2>) :
    RecyclerView.Adapter<OuterCompleteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingcomplete_layout, parent, false)
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
        fun bind(outerItem: MatchingCompleteFragment.OuterItem2, context: Context) {
            if (outerItem.innerItemList == null || outerItem.innerItemList.isEmpty()) {
                // 내부 아이템이 없는 경우
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
                textView2.text = outerItem.title
                imageView16.isClickable = false

            } else {
                // 내부 아이템이 있는 경우
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
                Log.d("OuterCompleteAdapter", "${outerItem.innerItemList.size}")

                // 여기서 outerItem의 내부 아이템 리스트를 사용하여 InnerAdapter 생성 및 설정
                val innerAdapter = InnerCompleteAdapter(outerItem.innerItemList)
                innerRecyclerView.layoutManager = LinearLayoutManager(context)  // 레이아웃 관리자 설정
                innerRecyclerView.adapter = innerAdapter

                textView3.text = "${outerItem.getItemCount()}건"
                // titletext5에 아이템 개수 표시
                titletext5.text = "${outerItem.getItemCount()}건"
                textView2.text = outerItem.title
                textView.text = outerItem.title
            }
        }
    }
}