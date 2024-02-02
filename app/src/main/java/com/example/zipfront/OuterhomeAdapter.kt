package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class OuterhomeAdapter(private val outerItemList: List<FragmentHome.OuterItem>) : RecyclerView.Adapter<OuterhomeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_matchinghome_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val outerItem = outerItemList[position]
        holder.bind(outerItem, position, holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return outerItemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.pose_rv)
        private val textView: TextView = itemView.findViewById(R.id.titletext4)

        fun bind(outerItem: FragmentHome.OuterItem, position: Int, context: Context) {
            val innerAdapter = InnerhomeAdapter(outerItem.innerItemList ?: emptyList())
            innerRecyclerView.adapter = innerAdapter
            textView.text = "${outerItem.title}"
        }
    }
}