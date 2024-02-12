package com.example.zipfront

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.SignInBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: SignInBinding
    private var isWritePasswordVisible = false

    private lateinit var user: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MyApplication을 초기화하는 코드가 여기에 있어야 합니다.
        MyApplication.initializeUser(this)

        user = MyApplication.getUser()
        editor = user.edit()

        // EditText를 binding으로 참조
        val writePassword = binding.writePassword
        val eyeImageView = binding.imageView24

        val imageButton5 = binding.imageButton5
        val text2 = binding.text2
        val imageButton1_1 = binding.imageButton

        val writeEmail = binding.writeEmail
        val idError = binding.idError

        // 초기에 비밀번호를 가리도록 설정
        writePassword.transformationMethod = PasswordTransformationMethod.getInstance()

        binding.imageView10.setOnClickListener {
            finish()
        }
        eyeImageView.setOnClickListener {
            togglePasswordVisibility(writePassword, eyeImageView)
        }

        binding.imageButton5.setOnClickListener {
            val email = binding.writeEmail.text.toString()
            val password = binding.writePassword.text.toString()

            val call = RetrofitObject.getRetrofitService.login(RetrofitClient2.Requestlogin(email, password))
            call.enqueue(object : Callback<RetrofitClient2.Responselogin> {
                override fun onResponse(call: Call<RetrofitClient2.Responselogin>, response: Response<RetrofitClient2.Responselogin>) {
                    Log.d("Retrofit21", response.toString())
                    if (response.isSuccessful) {
                        val response = response.body()
                        Log.d("Retrofit2", response.toString())
                        if(response != null){
                            if(response.isSuccess) {
                                val token = response.data.accessToken
                                val refreshtoken = response.data.refreshToken
                                editor.putString("email", email)
                                editor.putString("password", password)
                                editor.putString("jwt", token)
                                editor.putString("refreshtoken", refreshtoken)
                                editor.apply()
                                val intent =
                                    Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(intent)
                            }else{

                                Toast.makeText(this@SignInActivity, response.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.Responselogin>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }

        /*// imageButton5를 클릭했을 때 MainActivity로 이동
        imageButton5.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }*/

        // 비밀번호 잘못 입력했을 때 login_dialogview 코드 추가해야함!!!!!!!

        // text2를 클릭했을 때 SignUpSettingActivity로 화면 전환
        text2.setOnClickListener {
            val intent = Intent(this, SignUpSettingActivity::class.java)
            startActivity(intent)
        }

        // imageButton1_1를 클릭했을 때 SignUpSettingActivity로 화면 전환
        imageButton1_1.setOnClickListener {
            val intent = Intent(this, SignUpSettingActivity::class.java)
            startActivity(intent)
        }

        binding.imageView10.setOnClickListener {
            finish()
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
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // 아이디 작성 상태를 검사하여 포커스 여부와 상관없이 오류 메시지를 표시합니다.
        writeEmail.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            val emailText = writeEmail.text.toString()
            if (!emailText.contains("@") && emailText.isNotBlank()) {
                idError.visibility = View.VISIBLE
            } else {
                idError.visibility = View.GONE
            }
        }

        // 비밀번호 유효성 검사 함수
        fun isValidPassword(password: String): Boolean {
            val pattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!~@#\$%^&*(),.?\":{}|<>]).{8,}$")
            return pattern.matcher(password).matches()
        }

        /*// writePassword에 텍스트 변경 감지자 추가
        writePassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val passwordText = s.toString()
                if (passwordText.length < 8 || !isValidPassword(passwordText)) {
                    passwordErrorCondition.visibility = View.VISIBLE
                } else {
                    passwordErrorCondition.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })*/
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        // 비밀번호 보이기/감추기 기능 구현
        if (editText == binding.writePassword) {
            isWritePasswordVisible = !isWritePasswordVisible
            if (isWritePasswordVisible) {
                // 비밀번호를 보이도록 변경
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.eye_off)
            } else {
                // 비밀번호를 감추도록 변경
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                imageView.setImageResource(R.drawable.eye_on)
            }
        }
        // 커서를 마지막으로 이동하여 비밀번호 글자가 보이도록 함
        editText.setSelection(editText.text.length)
    }
}
