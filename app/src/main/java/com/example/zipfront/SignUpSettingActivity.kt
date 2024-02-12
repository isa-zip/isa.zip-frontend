package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.telecom.Call
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.SignUpSettingBinding
import retrofit2.Response
import java.util.regex.Pattern
import javax.security.auth.callback.Callback

class SignUpSettingActivity : AppCompatActivity() {
    private lateinit var binding: SignUpSettingBinding

    /*private lateinit var user: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor*/

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

        binding.imageButton5.setOnClickListener {
            val idErrorVisible = idError.visibility != View.VISIBLE
            val passwordMatchingVisible = passwordErrorMatching.visibility != View.VISIBLE
            val nicknameErrorVisible = nicknameError.visibility != View.VISIBLE
            val passwordConditionVisible = passwordErrorCondition.visibility != View.VISIBLE
            val isButtonEnabled = imageButton5.background.constantState != ContextCompat.getDrawable(this, R.drawable.btn_sign_up_setting_gray)?.constantState
            val isButtonTagBlue = imageButton5.tag == "blue"
            Log.d("Retrofit42", "updateProfile 함수 호출됨")

            val email = binding.writeEmail.text.toString()
            val password = binding.writePassword.text.toString()
            val nickName = binding.writeNickname.text.toString()
            Log.d("Retrofit48", "updateProfile 함수 호출됨")

            val call = RetrofitObject.getRetrofitService.setting(RetrofitClient2.Requestsetting(email, password, nickName))
            call.enqueue(object : retrofit2.Callback<RetrofitClient2.Responsesetting> {
                override fun onResponse(call: retrofit2.Call<RetrofitClient2.Responsesetting>, response: Response<RetrofitClient2.Responsesetting>) {
                    Log.d("Retrofit11", response.toString())
                    if (response.isSuccessful) {
                        val response = response.body()
                        Log.d("Retrofit22", response.toString())
                        if(response != null){
                            if(response.isSuccess) {
                                val intent = Intent(this@SignUpSettingActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(this@SignUpSettingActivity, response.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                override fun onFailure(call: retrofit2.Call<RetrofitClient2.Responsesetting>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
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
        } else {
            imageButton5.setImageResource(R.drawable.btn_sign_up_setting_gray)
            // 오류가 있을 때는 화면 전환을 막습니다.
            imageButton5.isEnabled = false
        }
    }
}