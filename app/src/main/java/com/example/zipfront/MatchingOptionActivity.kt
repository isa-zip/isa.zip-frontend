package com.example.zipfront

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.databinding.MatchingOptionBinding


class MatchingOptionActivity: AppCompatActivity() {
    lateinit var binding: MatchingOptionBinding
    val checkArray = BooleanArray(48)
    // 각 버튼에 대한 원래 값 저장 배열
    var originalWidthArray = IntArray(48)
    var originalBackgroundArray = arrayOfNulls<Drawable>(48)
    var originalTextColorArray = IntArray(48)

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

        binding.imageButton7.setOnClickListener{
            showCustomDialog()
        }

        binding.imageView10.setOnClickListener{
            finish()
        }
        val clickedColor = ContextCompat.getColor(this, R.color.zipblue01)

        // switch2의 체크 상태에 따라 sizeofroom과 sizeofroom2의 레이아웃을 조절
        binding.switch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // switch2가 체크된 경우 sizeofroom2를 보이게 함
                binding.sizeofroom2.visibility = View.VISIBLE
                binding.sizeofroom.visibility = View.GONE
            } else {
                // switch2가 체크되지 않은 경우 sizeofroom을 보이게 함
                binding.sizeofroom.visibility = View.VISIBLE
                binding.sizeofroom2.visibility = View.GONE
            }
        }

        // 각 버튼에 대한 원래 값 추출
        for (i in 0 until 48) {
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
                // 하나라도 버튼이 눌리면 imageButton7의 이미지 변경
                if (checkArray.any { it }) {
                    binding.imageButton7.setImageResource(R.drawable.btn__apply_active)
                } else {
                    binding.imageButton7.setImageResource(R.drawable.btn__apply_disabled)
                }
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
            val icon = resources.getDrawable(R.drawable.plus_circle_blue)
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

    private fun showCustomDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.option_dialogview, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val params =  alertDialog.window?.attributes
        params?.width = 296
        params?.height = 149
        alertDialog.window?.attributes = params

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
            // 확인 버튼을 눌렀을 때 수행할 동작
            setResult(Activity.RESULT_OK)
            finish()
            alertDialog.dismiss() // 다이얼로그 닫기
            // 추가적인 작업 수행 가능
        }

        // AlertDialog 표시
        alertDialog.show()
    }
}