package com.example.zipfront

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView


class ManagementAdapter(private val items: ArrayList<ManagementData>) : RecyclerView.Adapter<ManagementAdapter.ViewHolder>() {

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ManagementAdapter.ViewHolder, position: Int) {

        val item = items[position]
        val listener = View.OnClickListener { it ->
            Toast.makeText(it.context, "Clicked -> ID : ${item.info1}, Name : ${item.info2}, brokerItemID : ${item.brokerItemID}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView?.context, ManagementInfoActivity::class.java)
            //brokerItemID 보내기
            intent.putExtra("brokerItemID", item.brokerItemID)
            ContextCompat.startActivity(holder.itemView.context, intent, null)
        }


        holder.apply {
            bind(listener, item)
            itemView.tag = item
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_property_management, parent, false)
        return ManagementAdapter.ViewHolder(inflatedView)
    }

    // 각 항목에 필요한 기능을 구현
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private val image: ImageView = view.findViewById(R.id.property_img)
        private val textView1: TextView = itemView.findViewById(R.id.property_info_1)
        private val textView2: TextView = itemView.findViewById(R.id.property_info_2)
        private val textView3: TextView = itemView.findViewById(R.id.property_info_3)
        private val textView4: TextView = itemView.findViewById(R.id.property_info_4)
        private val textView5: TextView = itemView.findViewById(R.id.property_info_5)
        private val textView6: TextView = itemView.findViewById(R.id.property_tv)

        fun bind(listener: View.OnClickListener, item: ManagementData) {
            item.img.into(image)
            textView1.text = item.info1
            textView2.text = item.info2
            textView3.text = item.info3
            textView4.text = item.info4
            textView5.text = item.info5
            textView6.text = item.tv
            view.setOnClickListener(listener)
        }
    }
}
