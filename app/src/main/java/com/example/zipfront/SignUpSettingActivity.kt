package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.databinding.SignUpSettingBinding
import java.util.regex.Pattern

class SignUpSettingActivity : AppCompatActivity() {
    private lateinit var binding: SignUpSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignUpSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // EditText를 binding으로 참조
        val writePassword = binding.writePassword
        val checkPassword = binding.checkPassword
        val eyeImageView = binding.imageView23
        val eyeCheckImageView = binding.imageView24
        val writeEmail = binding.writeEmail
        val idError = binding.idError
        val passwordErrorMatching = binding.passwordErrorMatching
        val writeNickname = binding.writeNickname
        val nicknameError = binding.nicknameError
        val passwordErrorCondition = binding.passwordErrorCondition

        val imageButton5 = binding.imageButton5

        // 초기에 비밀번호를 가리도록 설정
        writePassword.transformationMethod = PasswordTransformationMethod.getInstance()
        checkPassword.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.imageView10.setOnClickListener {
            finish()
        }
        eyeImageView.setOnClickListener {
            togglePasswordVisibility(writePassword, eyeImageView)
        }
        eyeCheckImageView.setOnClickListener {
            togglePasswordVisibility(checkPassword, eyeCheckImageView)
        }

        // 아이디 작성 후 커서가 깜빡이지 않을 때 오류 메시지 표시
        writeEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val emailText = s.toString()
                if (!emailText.contains("@") && emailText.isNotBlank()) {
                    idError.visibility = View.VISIBLE
                } else {
                    idError.visibility = View.GONE
                }
                updateButtonBackground()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 비밀번호 유효성 검사 함수
        fun isValidPassword(password: String): Boolean {
            val pattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!~@#\$%^&*(),.?\":{}|<>]).{8,}$")
            return pattern.matcher(password).matches()
        }

        // writePassword에 텍스트 변경 감지자 추가
        writePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passwordText = s.toString()
                if (passwordText.length < 8 || !isValidPassword(passwordText)) {
                    binding.passwordErrorCondition.visibility = View.VISIBLE
                    checkPassword.isEnabled = false
                } else {
                    binding.passwordErrorCondition.visibility = View.GONE
                    checkPassword.isEnabled = true
                }
                updateButtonBackground()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // check_password 작성 후
        checkPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val checkPasswordText = s.toString()
                val writePasswordText = writePassword.text.toString()

                if (checkPasswordText != writePasswordText) {
                    passwordErrorMatching.visibility = View.VISIBLE
                } else {
                    passwordErrorMatching.visibility = View.GONE
                }
                updateButtonBackground()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // writeNickname에 특수 문자가 있는지 검사하여 오류 메시지 표시
        writeNickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val nicknameText = s.toString()
                if (containsSpecialCharacter(nicknameText)) {
                    nicknameError.visibility = View.VISIBLE
                } else {
                    nicknameError.visibility = View.GONE
                }
                updateButtonBackground()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 버튼 클릭 리스너 설정
        imageButton5.setOnClickListener {
            val idErrorVisible = idError.visibility != View.VISIBLE
            val passwordMatchingVisible = passwordErrorMatching.visibility != View.VISIBLE
            val nicknameErrorVisible = nicknameError.visibility != View.VISIBLE
            val passwordConditionVisible = passwordErrorCondition.visibility != View.VISIBLE
            val isButtonEnabled = imageButton5.background.constantState != ContextCompat.getDrawable(this, R.drawable.btn_sign_up_setting_gray)?.constantState

            if (idErrorVisible && passwordMatchingVisible && nicknameErrorVisible && passwordConditionVisible && isButtonEnabled && imageButton5.tag == "blue") {
                // 모든 오류가 없고 버튼이 활성화되며 버튼의 태그가 "blue"인 경우 SignUpSettingActivity로 화면 전환
                startActivity(Intent(this, SignUpSettingActivity::class.java))
            } else {
                // 조건에 해당하지 않는 경우 화면 전환을 막습니다.
                // 이때 아무런 작업을 수행하지 않습니다.
            }
        }

    }

    private fun containsSpecialCharacter(text: String): Boolean {
        val pattern = Pattern.compile("[!~@#$%^&*(),.?\":{}|<>]")
        val matcher = pattern.matcher(text)
        return matcher.find()
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        if (editText.transformationMethod == PasswordTransformationMethod.getInstance()) {
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.eye_on)
        } else {
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.eye_off)
        }
        editText.setSelection(editText.text.length)
    }

    private fun updateButtonBackground() {
        val idError = binding.idError
        val passwordErrorMatching = binding.passwordErrorMatching
        val nicknameError = binding.nicknameError
        val imageButton5 = binding.imageButton5

        val isIdErrorVisible = idError.visibility == View.VISIBLE
        val isPasswordErrorMatchingVisible = passwordErrorMatching.visibility == View.VISIBLE
        val isNicknameErrorVisible = nicknameError.visibility == View.VISIBLE

        if (!isIdErrorVisible && !isPasswordErrorMatchingVisible && !isNicknameErrorVisible) {
            imageButton5.setImageResource(R.drawable.btn_sign_up_setting_blue)
            // 모든 입력이 다 끝난 후에 어떤 오류 메시지도 생기지 않을 경우 버튼을 활성화합니다.
            imageButton5.isEnabled = true
            imageButton5.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            imageButton5.setImageResource(R.drawable.btn_sign_up_setting_gray)
            // 오류가 있을 때는 화면 전환을 막습니다.
            imageButton5.isEnabled = false
        }
    }
}