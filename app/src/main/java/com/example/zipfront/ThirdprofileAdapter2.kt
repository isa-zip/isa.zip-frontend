package com.example.zipfront

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView

class ThirdprofileAdapter2(private val itemList: MutableList<String> = mutableListOf()) : RecyclerView.Adapter<ThirdprofileAdapter2.ViewHolder>() {

    private val selectedItems = mutableListOf<Boolean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_matchingprofilenew, parent, false)
        return ViewHolder(view)
    }

    interface OnButtonClickListener {
        fun onButtonClick(position: Int)
    }
    var onButtonClickListener: OnButtonClickListener? = null

    // 선택한 아이템의 위치 저장
    private var selectedItemPosition: Int = -1

    fun setSelectedItemPosition(position: Int) {
        selectedItemPosition = position
        Log.d("MyApp2", "$selectedItemPosition")
        notifyDataSetChanged()
    }

    fun addItems(items: MutableList<String>) {
        itemList.addAll(items)
        selectedItems.addAll(List(items.size) { false }) // 초기에 모든 아이템을 선택되지 않은 상태로 초기화
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.bind(item)
        holder.updateBackground(selectedItems[position])
    }

    override fun getItemCount(): Int {
        Log.d("ThirdprofileAdapter", "${itemList.size}")
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView17)
        init {
            // 아이템 전체에 대한 클릭 리스너 설정
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ZItmeInfoActivity2::class.java)
                itemView.context.startActivity(intent)
            }

            imageView.setOnClickListener {
                onButtonClickListener?.onButtonClick(adapterPosition)
                selectedItems[adapterPosition] = !selectedItems[adapterPosition]
            }
        }

        fun bind(item: String) {
            textView.text = item
        }
        fun updateBackground(isSelected: Boolean) {
            val newBackgroundResource = if (isSelected) R.drawable.btn_add_gray else R.drawable.btn_add__1_
            // Update the background resource of the imageView
            imageView.setImageResource(newBackgroundResource)
            Log.d("MyApp3", "$isSelected")
        }
    }
}