package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SignUpBeforeActivity : AppCompatActivity() {
    private var isBlueChecked = true
    private lateinit var imageButton1: ImageButton
    private lateinit var imageButton2: ImageButton
    private lateinit var imageButton3: ImageButton
    private lateinit var imageButton4: ImageButton
    private lateinit var imageButton5: ImageButton
    private lateinit var imageButton1_1: ImageButton
    private lateinit var serviceTextView: TextView

    private var isButton2Blue = false
    private var isButton3Blue = false
    private var isButton4Blue = false
    private var isButton5Blue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up_before)

        imageButton1 = findViewById(R.id.imageButton1)
        imageButton2 = findViewById(R.id.imageButton2)
        imageButton3 = findViewById(R.id.imageButton3)
        imageButton4 = findViewById(R.id.imageButton4)
        imageButton5 = findViewById(R.id.imageButton5)
        imageButton1_1 = findViewById(R.id.imageButton1_1)
        serviceTextView = findViewById(R.id.some_id8)

        imageButton1.setOnClickListener {
            toggleButtonState(imageButton1)
        }

        imageButton2.setOnClickListener {
            toggleButtonState(imageButton2)
        }

        imageButton3.setOnClickListener {
            toggleButtonState(imageButton3)
        }

        imageButton4.setOnClickListener {
            toggleButtonState(imageButton4)
        }

        imageButton5.setOnClickListener {
            toggleButtonState(imageButton5)
        }

        serviceTextView.setOnClickListener {
            startActivity(Intent(this, SignUpServiceActivity::class.java))
        }

        val imageRight1: ImageView = findViewById(R.id.image_right1)
        imageRight1.setOnClickListener {
            startActivity(Intent(this, SignUpServiceActivity::class.java))
        }

        val imageRight2: ImageView = findViewById(R.id.image_right2)
        imageRight2.setOnClickListener {
            startActivity(Intent(this, SignUpPersonalActivity::class.java))
        }

        val imageRight3: ImageView = findViewById(R.id.image_right3)
        imageRight3.setOnClickListener {
            startActivity(Intent(this, SignUpMarketingActivity::class.java))
        }

        imageButton1_1.setOnClickListener {
            if (imageButton1_1.tag == "blue") {
                startActivity(Intent(this, SignUpSettingActivity::class.java))
            }
        }

        // imageView10을 클릭했을 때 SignMainActivity 화면 전환
        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun toggleButtonState(button: ImageButton) {
        when {
            button == imageButton1 -> {
                if (isBlueChecked) {
                    setAllButtonsToBlue()
                    setButton1_1ToBlue()
                    isButton2Blue = true
                    isButton3Blue = true
                    isButton4Blue = true
                    isButton5Blue = true
                } else {
                    setAllButtonsToGray()
                    setButton1_1ToGray()
                    isButton2Blue = false
                    isButton3Blue = false
                    isButton4Blue = false
                    isButton5Blue = false
                }
                isBlueChecked = !isBlueChecked
            }
            button == imageButton2 -> {
                isButton2Blue = !isButton2Blue
                setImageButtonColor(imageButton2, isButton2Blue)
                check234AndSetButton1_1Color()
                check2345Button1_1Color()
            }
            button == imageButton3 -> {
                isButton3Blue = !isButton3Blue
                setImageButtonColor(imageButton3, isButton3Blue)
                check234AndSetButton1_1Color()
                check2345Button1_1Color()
            }
            button == imageButton4 -> {
                isButton4Blue = !isButton4Blue
                setImageButtonColor(imageButton4, isButton4Blue)
                check234AndSetButton1_1Color()
                check2345Button1_1Color()
            }
            button == imageButton5 -> {
                isButton5Blue = !isButton5Blue
                setImageButtonColor(imageButton5, isButton5Blue)
                check2345Button1_1Color()
            }
        }
    }

    private fun setAllButtonsToBlue() {
        imageButton1.setImageResource(R.drawable.check_blue)
        imageButton2.setImageResource(R.drawable.check_blue)
        imageButton3.setImageResource(R.drawable.check_blue)
        imageButton4.setImageResource(R.drawable.check_blue)
        imageButton5.setImageResource(R.drawable.check_blue)
    }

    private fun setAllButtonsToGray() {
        imageButton1.setImageResource(R.drawable.check_gray)
        imageButton2.setImageResource(R.drawable.check_gray)
        imageButton3.setImageResource(R.drawable.check_gray)
        imageButton4.setImageResource(R.drawable.check_gray)
        imageButton5.setImageResource(R.drawable.check_gray)
    }

    private fun setButton1_1ToBlue() {
        imageButton1_1.setImageResource(R.drawable.btn_choice_blue)
        imageButton1_1.tag = "blue"
    }

    private fun setButton1_1ToGray() {
        imageButton1_1.setImageResource(R.drawable.btn_choice)
        imageButton1_1.tag = "gray"
    }

    private fun setImageButtonColor(button: ImageButton, isBlue: Boolean) {
        if (isBlue) {
            button.setImageResource(R.drawable.check_blue)
        } else {
            button.setImageResource(R.drawable.check_gray)
        }
    }

    private fun check234AndSetButton1_1Color() {
        if (isButton2Blue && isButton3Blue && isButton4Blue && isButton5Blue) {
            setButton1_1ToBlue()
        } else {
            setButton1_1ToGray()
        }
    }

    private fun check2345Button1_1Color() {
        if (isButton2Blue && isButton3Blue && isButton4Blue) {
            setButton1_1ToBlue()
        } else {
            setButton1_1ToGray()
        }
    }
}
