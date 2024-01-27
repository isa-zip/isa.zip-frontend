package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.FitstMenuCertifyBinding
import com.example.zipfront.databinding.FitstMenuSecessionBinding

class MenuSecessionActivity: AppCompatActivity() {
    lateinit var binding: FitstMenuSecessionBinding
    private var isChecked: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuSecessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView10.setOnClickListener {
            finish()
        }
        // 이미지 뷰 클릭 이벤트 리스너 설정
        binding.imageView25.setOnClickListener {
            // 토글 상태 변경
            isChecked = !isChecked

            // 이미지 변경
            if (isChecked) {
                binding.imageView25.setImageResource(R.drawable.check_blue)
                binding.button3.setBackgroundResource(R.drawable.profileroudradius_blue)
            } else {
                binding.imageView25.setImageResource(R.drawable.groupcheck_gray)
                binding.button3.setBackgroundResource(R.drawable.profileroudradius_gray)
            }
        }
        binding.button3.setOnClickListener {
            finish()
        }
    }
}