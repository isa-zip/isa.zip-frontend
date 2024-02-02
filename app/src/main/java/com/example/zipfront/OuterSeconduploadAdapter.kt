package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OuterSeconduploadAdapter(private val innerItems: List<String>,  private val onItemSelected: (List<String>) -> Unit) : RecyclerView.Adapter<OuterSeconduploadAdapter.ViewHolder>() {

    // UploadBottomsheetFragment 객체를 멤버 변수로 선언
    private val bottomSheetFragment = UploadBottomsheetFragment()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_uploaduser_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerItem = innerItems[position]
        holder.bind(innerItem, holder.itemView.context)
    }
    override fun getItemCount(): Int {
        return innerItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val layout1: ConstraintLayout = itemView.findViewById(R.id.layout1)
        private val layout3: ConstraintLayout = itemView.findViewById(R.id.layout3)
        private val textView: TextView = itemView.findViewById(R.id.textView30)
        private val textView2: TextView = itemView.findViewById(R.id.textView22)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView16)
        private val imageView2: ImageView = itemView.findViewById(R.id.imageView17)
        private val imageButton: ImageButton = itemView.findViewById(R.id.imageButton10)
        fun bind(innerItem: String, context: Context) {
            // 여기서 innerItem 사용하여 필요한 작업 수행
            layout1.visibility = View.VISIBLE
            layout3.visibility = View.GONE
            textView.text = innerItem
            textView2.text = innerItem

            imageView.setOnClickListener{
                layout1.visibility = View.GONE
                layout3.visibility = View.VISIBLE
            }
            imageView2.setOnClickListener{
                layout1.visibility = View.VISIBLE
                layout3.visibility = View.GONE
            }
            imageButton.setOnClickListener {
                bottomSheetFragment.show((context as AppCompatActivity).supportFragmentManager, bottomSheetFragment.tag)
            }
            bottomSheetFragment.onItemSelected = onItemSelected
        }
    }
}