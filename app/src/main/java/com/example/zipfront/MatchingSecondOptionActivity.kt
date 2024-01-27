package com.example.zipfront

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.ActivityMainBinding
import com.example.zipfront.databinding.ActivityMatchingoptionselectBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MatchingSecondOptionActivity : AppCompatActivity() {
    lateinit var binding: ActivityMatchingoptionselectBinding
    private lateinit var adapter: OuterSecondoptionAdapter
    private lateinit var outerItemList2: List<OuterItem2>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchingoptionselectBinding.inflate(layoutInflater)

        val title = intent.getStringExtra("title")
        val position = intent.getIntExtra("position", -1)

        if (!title.isNullOrBlank() && position != -1) {
            binding.textView9.text =  "${position + 1} $title"
        }

        // innerItems를 받아서 RecyclerView 설정
        val innerItems = intent.getStringArrayListExtra("innerItems")
        if (innerItems.isNullOrEmpty()) {
            binding.optionRv.visibility = View.GONE
        } else {
            setupRecyclerView(innerItems)
        }
        binding.imageView10.setOnClickListener{
            finish()
        }

        binding.imageView17.setOnClickListener{
            showCustomDialog()
        }
        setContentView(binding.root)
    }

    private fun setupRecyclerView(innerItems: List<String>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuterSecondoptionAdapter(innerItems)
        binding.optionRv.adapter = adapter
    }

    data class OuterItem2(val title: String, val innerItemList: List<String>? = null) {
        fun getItemCount(): Int {
            return innerItemList?.size ?: 0
        }
    }
    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_seconddialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 폭으로 설정
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT // 원하는 높이로 설정
        alertDialog.window?.attributes = layoutParams

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.imageButton8)
        val confirmButton = dialogView.findViewById<ImageButton>(R.id.imageButton9)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            // 취소 버튼을 눌렀을 때 수행할 동작
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // 확인 버튼 클릭 리스너 설정
        confirmButton.setOnClickListener {
            finish()
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
    }
}