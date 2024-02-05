package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class IsaScheduleAdapter(
    private val scheduleList: List<IsaScheduleItem>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<IsaScheduleAdapter.IsaScheduleViewHolder>() {

    private var selectedItemPosition: Int = -1
    private var isEditingMode: Boolean = false

    inner class IsaScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewDate: TextView = itemView.findViewById(R.id.textView22)
        val textViewDescription: TextView = itemView.findViewById(R.id.textView23)
        val circleImageView: ImageView = itemView.findViewById(R.id.circle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)

                if (isEditingMode) {
                    selectedItemPosition = position
                    notifyDataSetChanged()
                }
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

        // Set the background and image based on whether the item is selected or not
        if (position == selectedItemPosition) {
            val layout1: ConstraintLayout = holder.itemView.findViewById(R.id.layout1)
            layout1.setBackgroundResource(R.drawable.list3)

            val pointImageView: ImageView = holder.itemView.findViewById(R.id.point_gray)
            pointImageView.setImageResource(R.drawable.point_blue)
        } else {
            val layout1: ConstraintLayout = holder.itemView.findViewById(R.id.layout1)
            layout1.setBackgroundResource(R.drawable.list4)

            val pointImageView: ImageView = holder.itemView.findViewById(R.id.point_gray)
            pointImageView.setImageResource(R.drawable.point_gray)
        }

        // Define the action when the item is clicked
        holder.itemView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            // Ensure the clicked item is not already selected
            if (adapterPosition != selectedItemPosition) {
                // Update the selected item position
                selectedItemPosition = adapterPosition
                // Notify that the item has changed
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount() = scheduleList.size

    // 편집 모드를 설정합니다.
    fun setEditingMode(isEditing: Boolean) {
        isEditingMode = isEditing
        notifyDataSetChanged()
    }
}
