package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IsaScheduleAdapter(private val scheduleList: List<IsaScheduleItem>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<IsaScheduleAdapter.IsaScheduleViewHolder>() {

    private var selectedItemPosition: Int = -1

    inner class IsaScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val textViewDate: TextView = itemView.findViewById(R.id.textView22)
        val textViewDescription: TextView = itemView.findViewById(R.id.textView23)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)

                // 선택된 아이템의 위치를 갱신하고 색상을 변경합니다.
                val previousSelectedItemPosition = selectedItemPosition
                selectedItemPosition = position
                notifyDataSetChanged() // 모든 아이템의 배경을 새로 고칩니다.
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IsaScheduleViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_scheduleoption_layout, parent, false)
        return IsaScheduleViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IsaScheduleViewHolder, position: Int) {
        val currentItem = scheduleList[position]
        holder.textViewDate.text = currentItem.date
        holder.textViewDescription.text = currentItem.description

        /*// 선택된 아이템인 경우
        if (position == selectedItemPosition) {
            holder.itemView.setBackgroundResource(R.drawable.point_blue)
            holder.itemView.setBackgroundResource(R.drawable.list3)
        } else {
            holder.itemView.setBackgroundResource(R.drawable.point_gray)
            holder.itemView.setBackgroundResource(R.drawable.list4)
        }*/
    }

    override fun getItemCount() = scheduleList.size
}