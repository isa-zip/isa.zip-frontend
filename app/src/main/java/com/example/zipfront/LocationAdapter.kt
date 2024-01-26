package com.example.zipfront

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnItemClick {
    fun onClick(location: Int)
}

class LocationAdapter(private var locationSet: List<Location>, private var onItemClick: OnItemClick) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val location: TextView = view.findViewById(R.id.location_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_location, parent, false)
        return ViewHolder(view).listen { position, type -> setLocation(locationSet[position].id) }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.location.text = "${locationSet[position].sido} ${locationSet[position].sigun} ${locationSet[position].dongmyeon} ${locationSet[position].li}"
    }

    override fun getItemCount(): Int = locationSet.size

//    fun filterList(filteredList: List<Location>) {
//        locationSet = filteredList
//        notifyDataSetChanged()
//    }

    private fun <T: RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener { event.invoke(adapterPosition, itemViewType) }
        return this
    }

    // 지역 설정
    private fun setLocation(id: Int) {
        onItemClick.onClick(id)
    }
}