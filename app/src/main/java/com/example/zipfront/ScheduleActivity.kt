package com.example.zipfront

import android.app.TaskStackBuilder
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.connection.RetrofitClient2
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ScheduleActivity : AppCompatActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var selectedDateTextView: TextView
    private lateinit var registerButton: ImageButton
    private lateinit var selectedDate: String // 선택된 날짜를 저장할 변수 추가

    private lateinit var user: SharedPreferences
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        // user 객체 초기화
        user = getSharedPreferences("user", MODE_PRIVATE)
        token = user.getString("jwt", "").toString() // token 초기화

        // 선택된 날짜 초기화
        selectedDate = ""

        calendarView = findViewById(R.id.calendarView)
        selectedDateTextView = findViewById(R.id.textViewInside)
        registerButton = findViewById(R.id.imageButton5)

        // 초기에는 버튼을 비활성화
        registerButton.isEnabled = false

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 달력의 날짜가 변경될 때의 동작
            selectedDate = formatSelectedDate(year, month, dayOfMonth)
            selectedDateTextView.text = selectedDate
            updateRegisterButtonImage()
        }

        // 이미지 버튼 클릭 시 동작
        registerButton.setOnClickListener {
            // 선택한 날짜를 IsaScheduleActivityHome 전달
            val intent = Intent(this, IsaScheduleActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)


            /*
            val period = "ONE_MONTH"
            Log.d("Retrofit33", period)
            // 현재 선택된 날짜 가져오기
            val moveDate = getSelectedDate()
            Log.d("Retrofit3333", moveDate)

            val call = RetrofitObject.getRetrofitService.schedule("Bearer $token", RetrofitClient2.Requestschedule(period, moveDate))
            call.enqueue(object : Callback<RetrofitClient2.Responseschedule> {
                override fun onResponse(
                    call: Call<RetrofitClient2.Responseschedule>,
                    response: Response<RetrofitClient2.Responseschedule>
                ) {
                    Log.d("Retrofit31", response.toString())
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Retrofit3", responseBody.toString())
                        if (responseBody != null && responseBody.isSuccess) {
                            val intent = Intent(this@ScheduleActivity, IsaScheduleActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@ScheduleActivity,
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RetrofitClient2.Responseschedule>, t: Throwable) {
                    val errorMessage = "Call Failed: ${t.message}"
                    Log.d("Retrofit", errorMessage)
                }
            })
             */

        }

        // imageView10을 클릭했을 때 액티비티 종료
        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }
    }

    // 현재 선택된 날짜에서 1달을 뺀 날짜 가져오기
    private fun getSelectedDate(): String {
        // 현재 선택된 날짜 가져오기
        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        val selectedDate = sdf.parse(selectedDate) ?: Date() // selectedDate를 Date 객체로 파싱

        // Calendar 객체를 사용하여 날짜 조작
        val calendar = Calendar.getInstance()
        calendar.time = selectedDate
        calendar.add(Calendar.MONTH, -1)

        // 변경된 날짜를 다시 String 형식으로 반환
        return sdf.format(calendar.time)
    }

    private fun formatSelectedDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        return "${year}.${month + 1}.$dayOfMonth"
    }

    private fun updateRegisterButtonImage() {
        // 선택된 날짜가 있을 때만 버튼을 활성화
        registerButton.isEnabled = true
        // 아무 날짜가 선택되었을 때 항상 btn_register_blue.xml로 버튼 이미지 변경
        registerButton.setImageResource(R.drawable.btn_register_blue)
    }
}
