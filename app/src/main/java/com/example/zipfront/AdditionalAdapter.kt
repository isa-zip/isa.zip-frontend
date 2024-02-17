package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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

        // ViewHolder 내부의 circleImageView 참조
        val textView65: TextView = holder.itemView.findViewById(R.id.textView65)
        val textView67: TextView = holder.itemView.findViewById(R.id.textView67)
        val road: TextView = holder.itemView.findViewById(R.id.road)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(private val binding: ItemAddressoptionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            // 각 아이템에 대한 바인딩 작업 수행
            val items = item.split(", ") // 쉼표를 기준으로 문자열을 분리
            if (items.size >= 3) { // 최소한 3개의 요소가 있는지 확인
                binding.textView67.text = items[0] // 지번
                binding.road.text = items[1] // 도로명 주소
                binding.textView65.text = items[2] // 우편번호
            }
        }
    }

}