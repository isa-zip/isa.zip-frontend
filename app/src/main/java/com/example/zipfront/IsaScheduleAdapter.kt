package com.example.zipfront

import android.app.Dialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
    private var isEditingClicked: Boolean = false

    inner class IsaScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textViewDate: TextView = itemView.findViewById(R.id.textView22)
        val textViewDescription: TextView = itemView.findViewById(R.id.textView23)
        val circleImageView: ImageView = itemView.findViewById(R.id.edit_circle)

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
                } else {
                    // 편집 모드가 아닌 경우에는 항상 선택된 아이템의 배경을 btn_agent3로 설정합니다.
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

        // ViewHolder 내부의 circleImageView 참조
        val circleImageView: ImageView = holder.itemView.findViewById(R.id.edit_circle)
        val layout1: ConstraintLayout = holder.itemView.findViewById(R.id.layout1)
        val pointImageView: ImageView = holder.itemView.findViewById(R.id.point_gray)

        // 편집 모드일 때 btn_agent3, point_blue
        if (isEditingClicked && isEditingMode) {
            circleImageView.visibility = View.VISIBLE
            if (position == selectedItemPosition) {
                layout1.setBackgroundResource(R.drawable.btn_agent3)
                pointImageView.setImageResource(R.drawable.point_blue)
            } else {
                layout1.setBackgroundResource(R.drawable.list4)
                pointImageView.setImageResource(R.drawable.point_gray)
            }
        }
        // 편집 모드 아닐 때 list3, point_blue
        else {
            circleImageView.visibility = View.GONE
            if (position == selectedItemPosition) {
                layout1.setBackgroundResource(R.drawable.list3)
                pointImageView.setImageResource(R.drawable.point_blue)
            } else {
                layout1.setBackgroundResource(R.drawable.list4)
                pointImageView.setImageResource(R.drawable.point_gray)
            }
        }

        // ViewHolder 내부의 circleImageView에 클릭 리스너 설정
        circleImageView.setOnClickListener {
            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.bottomsheet_dialog_component)

            // Dialog의 창 위치 설정
            val window = dialog.window
            val layoutParams = window?.attributes
            layoutParams?.apply {
                gravity = Gravity.BOTTOM // 화면 아래에 위치하도록 설정
                width = WindowManager.LayoutParams.MATCH_PARENT
            }
            window?.attributes = layoutParams

            dialog.show()
        }

        holder.itemView.setOnClickListener {
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != selectedItemPosition) {
                selectedItemPosition = adapterPosition
                notifyDataSetChanged()
            }
        }
    }

    // 편집 텍스트를 누를 때 선택된 아이템의 배경색과 편집 모드를 변경합니다.
    fun setEditingClicked(isClicked: Boolean) {
        isEditingClicked = isClicked
        notifyDataSetChanged()
    }

    override fun getItemCount() = scheduleList.size

    // 편집 모드를 설정합니다.
    fun setEditingMode(isEditing: Boolean) {
        isEditingMode = isEditing
        notifyDataSetChanged()
    }

}
