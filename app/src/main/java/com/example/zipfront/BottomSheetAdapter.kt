package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BottomSheetAdapter(private val fragment: UploadBottomsheetFragment): RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private val items = listOf("Item 1", "Item 2")
    private var selectedItem: String? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchinguploadbottom, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getSelectedItem(): String? {
        return selectedItem
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)
        private val bottomSheetLayout: View = itemView.findViewById(R.id.bottomsheetlayout)
        private var isChecked: Boolean = false

        fun bind(item: String) {
            textView.text = item
            itemView.setOnClickListener {
                isChecked = !isChecked // 토글

                // 아이템을 클릭했을 때 배경 변경
                if (isChecked) {
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background_blue)
                    selectedItem = item // 선택한 아이템 정보 저장
                } else {
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background)
                    selectedItem = null // 선택 취소 시에는 null로 설정
                }
                fragment.updateBtnCloseBackground()
            }
        }
    }
}