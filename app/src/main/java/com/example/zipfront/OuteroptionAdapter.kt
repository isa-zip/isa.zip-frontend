package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class OuteroptionAdapter(private val outerItemList: List<MatchingStillFragment.OuterItem>) : RecyclerView.Adapter<OuteroptionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchingoption_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val outerItem = outerItemList[position]
        holder.bind(outerItem)
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
        private val titletext5: TextView = itemView.findViewById(R.id.titletext5)

        fun bind(outerItem: MatchingStillFragment.OuterItem) {
            if (outerItem.innerItemList == null || outerItem.innerItemList.isEmpty()) {
                // 내부 아이템이 없는 경우
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
                textView2.text = outerItem.title
            } else {
                // 내부 아이템이 있는 경우
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE

                // 여기서 outerItem의 내부 아이템 리스트를 사용하여 InnerAdapter 생성 및 설정
                val innerAdapter = InneroptionAdapter(outerItem.innerItemList)
                innerRecyclerView.adapter = innerAdapter

                // titletext5에 아이템 개수 표시
                titletext5.text = "${outerItem.getItemCount()}건"
                textView.text = outerItem.title
            }
        }
    }
}