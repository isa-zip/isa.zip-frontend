package com.example.zipfront

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class CurrentSearchAdapter(val context: Context, val currentSearchList: ArrayList<CurrentSearch>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_current_search, null)
        val location = view.findViewById<TextView>(R.id.location_tv)
        val delete = view.findViewById<ImageView>(R.id.delete_iv)

        val locationList = currentSearchList[position]

        location.text = locationList.location
        delete.setImageResource(locationList.delete)

        return view
    }
    override fun getCount(): Int {
        return currentSearchList.size
    }

    override fun getItem(position: Int): Any {
        return currentSearchList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }


}