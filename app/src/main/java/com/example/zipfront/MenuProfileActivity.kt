package com.example.zipfront

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.FitstMenuProfileBinding
import kotlinx.coroutines.CoroutineStart
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.regex.Pattern
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.InputStream

class MenuProfileActivity : AppCompatActivity() {
    lateinit var binding: FitstMenuProfileBinding
    private val PICK_IMAGE_REQUEST = 1 // 이미지를 선택하는 요청 코드
    var isPasswordVisible = false
    private lateinit var user: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var token: String
    private lateinit var password: String
    private lateinit var email: String
    private lateinit var nickName: String
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FitstMenuProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 사용자 SharedPreferences 초기화
        user = MyApplication.getUser()
        token = user.getString("jwt", "").toString()
        password = user.getString("password", "").toString()
        email = user.getString("email", "").toString()
        nickName = user.getString("nickName", "").toString()

        binding.textchecking.visibility = View.GONE
        binding.textchecking2.visibility = View.GONE
        // EditText를 binding으로 참조
        val textPassword = binding.textPassword
        val textPasswordCheck = binding.textPasswordcheck
        val eyeImageView = binding.imageView23
        val eyeCheckImageView = binding.imageView24

        val button2 = binding.button2

        textPassword.setText(password)

        binding.textID.hint = nickName
        binding.textID2.text = email

        binding.imageView10.setOnClickListener {
            finish()
        }

        binding.imageView18.setOnClickListener {
            openGallery()
        }

        eyeImageView.setOnClickListener {
            togglePasswordVisibility(textPassword, eyeImageView)
        }
        eyeCheckImageView.setOnClickListener {
            togglePasswordVisibility(textPasswordCheck, eyeCheckImageView)
        }

        textPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // 입력된 비밀번호가 조건을 만족하는지 확인
                val isValidPassword = isPasswordValid(textPassword.text.toString())

                // 비밀번호가 조건을 만족하지 않으면 textchecking의 visibility를 View.VISIBLE로 설정
                if (!isValidPassword) {
                    binding.textchecking.visibility = View.VISIBLE
                } else {
                    // 비밀번호가 조건을 만족하면 textchecking의 visibility를 View.GONE으로 설정
                    binding.textchecking.visibility = View.GONE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        // textPasswordCheck의 텍스트 변경 감지
        textPasswordCheck.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // textPassword와 textPasswordCheck의 값이 같을 때 배경을 업데이트
                if (textPassword.text.toString() == textPasswordCheck.text.toString()) {
                    button2.setBackgroundResource(R.drawable.profileroudradius_blue)
                    binding.textchecking2.visibility = View.GONE
                }
                else {
                    button2.setBackgroundResource(R.drawable.profileroudradius_gray)
                    binding.textchecking2.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.button2.setOnClickListener {
            val password2 = textPassword.text.toString()
            updateProfile(selectedImageUri, password2)
        }
    }

    private fun updateButtonBackground(editText: EditText, button: Button) {
        // 텍스트가 비어있으면 버튼 배경을 profileroudradius_gray로, 비어있지 않으면 profileroudradius_blue로 설정
        if (editText.text.isNotEmpty()) {
            button.setBackgroundResource(R.drawable.profileroudradius_blue)
        } else {
            button.setBackgroundResource(R.drawable.profileroudradius_gray)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        // 비밀번호가 8자리 이상이며 영문, 숫자, 특수문자를 포함하는지 확인하는 로직
        val pattern = Pattern.compile("(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@$!%*?&]{8,}")
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }

    private fun togglePasswordVisibility(editText: EditText, imageView: ImageView) {
        // 비밀번호 보이기/감추기 기능 구현
        isPasswordVisible = !isPasswordVisible

        if (isPasswordVisible) {
            // 비밀번호를 보이도록 변경
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.eye_on)
        } else {
            // 비밀번호를 감추도록 변경
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            imageView.setImageResource(R.drawable.eye_off)
        }

        // 커서를 마지막으로 이동하여 비밀번호 글자가 보이도록 함
        editText.setSelection(editText.text.length)
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.data!!

            // 이제 selectedImageUri를 사용하여 이미지를 처리하고, ImageView에 설정할 수 있습니다.
            // 예시로 Glide 사용하는 방법:
            Glide.with(this)
                .load(selectedImageUri)
                .into(binding.imageView18)
        }
    }
    private fun updateProfile(imageUri: Uri?, password: String) {
        Log.d("Retrofit42", "updateProfile 함수 호출됨")
        editor = user.edit()
        val nickName = binding.textID.text.toString()
        val nickNamePart = nickName.toRequestBody("text/plain".toMediaTypeOrNull())

        val passwordPart = password.toRequestBody("text/plain".toMediaTypeOrNull())

        if (imageUri == null) {
            // 이미지 URI가 null이면 서버에는 이미지를 업로드하지 않고, 닉네임과 비밀번호만 전송
            val call = RetrofitObject.getRetrofitService.editprofile("Bearer $token", null, nickNamePart, passwordPart)
            call.enqueue(object : Callback<RetrofitClient2.ResponseProfile> {
                override fun onResponse(call: Call<RetrofitClient2.ResponseProfile>, response: Response<RetrofitClient2.ResponseProfile>) {
                    Log.d("Retrofit430", response.toString())
                    if (response.isSuccessful) {
                        Log.d("Retrofit410", response.toString())
                        val responseBody = response.body()
                        Log.d("Retrofit40", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            editor.putString("nickName", nickName)
                            editor.apply()
                            finish()
                        } else {
                            Toast.makeText(
                                this@MenuProfileActivity,
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.ResponseProfile>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit0", errorMessage)
                }
            })
        } else {
            val compressedImageData = compressAndEncodeImage(imageUri)
            // 이미지 URI가 null이 아니면 이미지를 서버에 업로드하고 닉네임과 비밀번호도 함께 전송
            val file = File(cacheDir, "image.jpg")
            file.writeBytes(Base64.decode(compressedImageData, Base64.DEFAULT))

            // 이미지 파일을 서버로 전송
            val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("userImg", file.name, requestFile)

            Log.d("Retrofit431", body.toString())
            val call = RetrofitObject.getRetrofitService.editprofile("Bearer $token",body, nickNamePart, passwordPart)
            call.enqueue(object : Callback<RetrofitClient2.ResponseProfile> {
                override fun onResponse(call: Call<RetrofitClient2.ResponseProfile>, response: Response<RetrofitClient2.ResponseProfile>) {
                    Log.d("Retrofit43", response.toString())
                    if (response.isSuccessful) {
                        Log.d("Retrofit41", response.toString())
                        val responseBody = response.body()
                        Log.d("Retrofit4", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            editor.putString("nickName", nickName)
                            editor.apply()
                            finish()
                        } else {
                            Toast.makeText(
                                this@MenuProfileActivity,
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.ResponseProfile>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
        }
    }
    private fun getRealPathFromUri(uri: Uri): String? {
        val context = applicationContext
        val contentResolver = context.contentResolver
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                return it.getString(columnIndex)
            }
        }
        return null
    }
    @OptIn(kotlin.io.encoding.ExperimentalEncodingApi::class)
    fun encodeImageToBase64(imageUri: Uri): String {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bytes = inputStream?.readBytes()
        inputStream?.close()

        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
    private fun compressAndEncodeImage(imageUri: Uri): String? {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        val maxHeight = 1024.0f
        val maxWidth = 1024.0f
        val scale = Math.min(maxWidth / bitmap.width, maxHeight / bitmap.height)

        val matrix = Matrix()
        matrix.postScale(scale, scale)

        val scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        val byteArrayOutputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream) // 조정 가능한 압축률 및 포맷 설정
        val byteArray = byteArrayOutputStream.toByteArray()

        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}