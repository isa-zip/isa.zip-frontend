package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BottomSheetAdapter(private val fragment: UploadBottomsheetFragment): RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    private val items = listOf("Item 1", "Item 2")
    private val selectedItems = mutableListOf<String>()

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

    fun getSelectedItems(): List<String> {
        return selectedItems.toList()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView28)
        private val bottomSheetLayout: View = itemView.findViewById(R.id.bottomsheetlayout)
        private var isChecked: Boolean = false

        fun bind(item: String) {
            textView.text = item
            itemView.setOnClickListener {
                if (selectedItems.contains(item)) {
                    // 이미 선택된 아이템이면 제거
                    selectedItems.remove(item)
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background)
                } else {
                    // 선택되지 않은 아이템이면 추가
                    selectedItems.add(item)
                    bottomSheetLayout.setBackgroundResource(R.drawable.rounded_background_blue)
                }

                fragment.updateBtnCloseBackground()
            }
        }
    }
}