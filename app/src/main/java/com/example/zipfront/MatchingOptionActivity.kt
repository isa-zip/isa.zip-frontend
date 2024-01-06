package com.example.zipfront

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.databinding.MatchingOptionBinding


class MatchingOptionActivity: AppCompatActivity() {
    lateinit var binding: MatchingOptionBinding
    val checkArray = BooleanArray(11)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray = IntArray(11)
    var originalBackgroundArray = arrayOfNulls<Drawable>(11)
    var originalTextColorArray = IntArray(11)

    val checkArray2 = BooleanArray(3)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray2 = IntArray(3)
    var originalBackgroundArray2 = arrayOfNulls<Drawable>(3)
    var originalTextColorArray2 = IntArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MatchingOptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.constraintLayout3.visibility = View.GONE
        binding.constraintLayout4.visibility = View.GONE
        binding.constraintLayout5.visibility = View.GONE

        val clickedColor = ContextCompat.getColor(this, R.color.zipblue01)

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 11) {
            val buttonId = resources.getIdentifier("myButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

            originalWidthArray[i] = button.layoutParams.width
            originalBackgroundArray[i] = button.background
            originalTextColorArray[i] = button.currentTextColor
            checkArray[i] = false // 초기값은 모두 false로 설정

            button.setOnClickListener {
                toggleButtonClickState(
                    checkArray[i],
                    originalWidthArray[i],
                    originalBackgroundArray[i],
                    originalTextColorArray[i],
                    clickedColor,
                    button,
                    i
                )
            }
        }

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 3) {
            val buttonId = resources.getIdentifier("tradeButton${i + 1}", "id", packageName)
            val button = findViewById<Button>(buttonId)

            originalWidthArray2[i] = button.layoutParams.width
            originalBackgroundArray2[i] = button.background
            originalTextColorArray2[i] = button.currentTextColor
            checkArray2[i] = false // 초기값은 모두 false로 설정

            button.setOnClickListener {
                toggleButtonClickState2(
                    checkArray2[i],
                    originalWidthArray2[i],
                    originalBackgroundArray2[i],
                    originalTextColorArray2[i],
                    clickedColor,
                    button,
                    i
                )
                toggleConstraintLayoutVisibility2(i)
            }
        }
    }

    private fun toggleConstraintLayoutVisibility2(index: Int) {
        when (index) {
            0 -> {
                binding.constraintLayout3.visibility = if (binding.constraintLayout3.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            1 -> {
                binding.constraintLayout4.visibility = if (binding.constraintLayout4.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            2 -> {
                binding.constraintLayout5.visibility = if (binding.constraintLayout5.visibility == View.VISIBLE) View.GONE else View.VISIBLE
            }
            else -> {
            }
        }
    }

    private fun toggleButtonClickState(
        isChecked: Boolean,
        originalWidth: Int,
        originalBackground: Drawable?,
        originalTextColor: Int,
        clickedColor: Int,
        button: Button,
        index: Int
    ) {
        if (!isChecked) {
            // 클릭 시 아이콘 추가
            val icon = resources.getDrawable(R.drawable.plus_circle)
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

            // 클릭 시 버튼의 넓이를 기존 넓이에서 16 추가
            val newWidth = originalWidth + 16
            val layoutParams = button.layoutParams
            layoutParams.width = newWidth
            button.layoutParams = layoutParams

            // 클릭 시 버튼의 배경 변경
            button.setBackgroundResource(R.drawable.click_button_roundcircle)

            // 클릭 시 텍스트 색상 변경
            button.setTextColor(clickedColor)
        } else {
            // 클릭 시 원래의 상태로 복원
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            val layoutParams = button.layoutParams
            layoutParams.width = originalWidth
            button.layoutParams = layoutParams

            button.background = originalBackground

            button.setTextColor(originalTextColor)
        }

        // 토글 상태 변경
        checkArray[index] = !isChecked
    }

    private fun toggleButtonClickState2(
        isChecked: Boolean,
        originalWidth: Int,
        originalBackground: Drawable?,
        originalTextColor: Int,
        clickedColor: Int,
        button: Button,
        index: Int
    ) {
        if (!isChecked) {
            // 클릭 시 아이콘 추가
            val icon = resources.getDrawable(R.drawable.plus_circle)
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)

            // 클릭 시 버튼의 넓이를 기존 넓이에서 16 추가
            val newWidth = originalWidth + 16
            val layoutParams = button.layoutParams
            layoutParams.width = newWidth
            button.layoutParams = layoutParams

            // 클릭 시 버튼의 배경 변경
            button.setBackgroundResource(R.drawable.click_button_roundcircle)

            // 클릭 시 텍스트 색상 변경
            button.setTextColor(clickedColor)
        } else {
            // 클릭 시 원래의 상태로 복원
            button.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)

            val layoutParams = button.layoutParams
            layoutParams.width = originalWidth
            button.layoutParams = layoutParams

            button.background = originalBackground

            button.setTextColor(originalTextColor)
        }

        // 토글 상태 변경
        checkArray2[index] = !isChecked
    }
}