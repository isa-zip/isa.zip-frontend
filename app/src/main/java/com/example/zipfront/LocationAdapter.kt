package com.example.zipfront

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.SearchLocationActivity
import com.example.zipfront.databinding.ActivitySearchlocationBinding

interface OnItemClick {
    fun onClick(location: Int)
}

class LocationAdapter(private var locationSet: List<Location>, private var onItemClick: OnItemClick) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private var editTextValue: String = ""

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val location: TextView = view.findViewById(R.id.location_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return ViewHolder(view).listen { position, type -> setLocation(locationSet[position].id) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text = "${locationSet[position].sido} ${locationSet[position].sigun} ${locationSet[position].dongmyeon} ${locationSet[position].li}"


        val spannableString = SpannableString(holder.location.text)

        Log.d("글씨", editTextValue)

        // 중간 글자의 시작 위치
        val start = holder.location.text.indexOf(editTextValue, ignoreCase = true)
        // 중간 글자의 끝 위치
        val end = start + editTextValue.length

        // ForegroundColorSpan을 사용하여 특정 범위의 글자 색상 변경
        val colorSpan = ForegroundColorSpan(ContextCompat.getColor(holder.itemView.context, R.color.zipblue01))
        spannableString.setSpan(colorSpan, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        // SpannableString을 텍스트뷰에 설정
        holder.location.text = spannableString

    }

    override fun getItemCount(): Int = locationSet.size

    fun filterList(filteredList: List<Location>) {
        locationSet = filteredList


        notifyDataSetChanged()
    }

    private fun <T: RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener { event.invoke(adapterPosition, itemViewType) }
        return this
    }

    // 지역 설정
    private fun setLocation(id: Int) {
        onItemClick.onClick(id)
    }


    fun setEditTextValue(value: String) {
        editTextValue = value
        notifyDataSetChanged() // 데이터가 변경될 때 리사이클러뷰 갱신
    }
}