package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.databinding.AddressRecycleerviewBinding
import com.example.zipfront.databinding.ItemAddressoptionLayoutBinding

class AdditionalAdapter(private val context: Context, private val itemList: List<String>) : RecyclerView.Adapter<AdditionalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAddressoptionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itemList[position])

        // 아이템 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            val selectedItem = itemList[position]

            // AdditionalActivity2로 이동하는 인텐트 생성 및 전달
            val intent = Intent(context, AdditionalActivity2::class.java)
            intent.putExtra("selectedItem", selectedItem)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemAddressoptionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            // 각 아이템에 대한 바인딩 작업 수행
            binding.textView65.text = item
        }
    }
}