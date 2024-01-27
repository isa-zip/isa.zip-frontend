package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SignUpBeforeActivity : AppCompatActivity() {
    private var isBlueChecked = true
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton
    private lateinit var imageButton4: ImageButton
    private lateinit var imageButton5: ImageButton
    private lateinit var imageButton1_1: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_before) // sign_up_before.xml 파일과 연결

        val imageButton1: ImageButton = findViewById(R.id.imageButton1)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)
        imageButton4 = findViewById(R.id.imageButton4)
        imageButton5 = findViewById(R.id.imageButton5)
        imageButton1_1 = findViewById(R.id.imageButton1_1)

        imageButton1.setOnClickListener {
            // 이미지 변경
            if (isBlueChecked) {
                imageButton1.setImageResource(R.drawable.check_blue)
                imageButton2.setImageResource(R.drawable.check_blue)
                imageButton3.setImageResource(R.drawable.check_blue)
                imageButton4.setImageResource(R.drawable.check_blue)
                // imageButton1이 눌렸을 때에는 무조건 btn_choice가 btn_choice_blue로 바뀜
                imageButton1_1.setImageResource(R.drawable.btn_choice_blue)
            } else {
                imageButton1.setImageResource(R.drawable.check_gray)
                imageButton2.setImageResource(R.drawable.check_gray)
                imageButton3.setImageResource(R.drawable.check_gray)
                imageButton4.setImageResource(R.drawable.check_gray)
                // imageButton1이 눌리지 않았을 때에는 btn_choice를 표시
                imageButton1_1.setImageResource(R.drawable.btn_choice)
            }
            // 상태 변경
            isBlueChecked = !isBlueChecked
        }

        // 두 번째 버튼 눌렸을 때
        imageButton2.setOnClickListener {
            if (isBlueChecked) {
                imageButton2.setImageResource(R.drawable.check_blue)
            } else {
                imageButton2.setImageResource(R.drawable.check_gray)
            }
            isBlueChecked = !isBlueChecked
        }

        // 세 번째 버튼 눌렸을 때
        imageButton3.setOnClickListener {
            if (isBlueChecked) {
                imageButton3.setImageResource(R.drawable.check_blue)
            } else {
                imageButton3.setImageResource(R.drawable.check_gray)
            }
            isBlueChecked = !isBlueChecked
        }

        // 네 번째 버튼 눌렸을 때
        imageButton4.setOnClickListener {
            if (isBlueChecked) {
                imageButton4.setImageResource(R.drawable.check_blue)
            } else {
                imageButton4.setImageResource(R.drawable.check_gray)
            }
            isBlueChecked = !isBlueChecked
        }


        // 다섯 번째 버튼만 색깔이 바뀌도록 설정
        imageButton5.setOnClickListener {
            if (isBlueChecked) {
                imageButton5.setImageResource(R.drawable.check_blue)
            } else {
                imageButton5.setImageResource(R.drawable.check_gray)
            }
            isBlueChecked = !isBlueChecked
        }

        // 서비스 이용약관 동의 화살표 눌렀을 때
        val imageRight1: ImageView = findViewById(R.id.image_right1)
        imageRight1.setOnClickListener {
            val intent = Intent(this, SignUpServiceActivity::class.java)
            startActivity(intent)
        }

        // 개인정보 수집 화살표 눌렀을 때
        val imageRight2: ImageView = findViewById(R.id.image_right2)
        imageRight2.setOnClickListener {
            val intent = Intent(this, SignUpPersonalActivity::class.java)
            startActivity(intent)
        }

        // 마케팅 정보 수집 화살표 눌렀을 때
        val imageRight3: ImageView = findViewById(R.id.image_right3)
        imageRight3.setOnClickListener {
            val intent = Intent(this, SignUpMarketingActivity::class.java)
            startActivity(intent)
        }

        // 버튼 눌렀을 때
        imageButton1_1.setOnClickListener {
            val intent = Intent(this, SignUpSettingActivity::class.java)
            startActivity(intent)
        }
    }
}
