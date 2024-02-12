package com.example.zipfront

import android.app.TaskStackBuilder
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
    private lateinit var cancel: ImageButton
    private lateinit var selectedDate: String // 선택된 날짜를 저장할 변수 추가

    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        calendarView = findViewById(R.id.calendarView)
        selectedDateTextView = findViewById(R.id.textViewInside)
        registerButton = findViewById(R.id.imageButton5)
        cancel = findViewById(R.id.cancel)

        // 초기에는 버튼을 비활성화
        registerButton.isEnabled = false

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            // 달력의 날짜가 변경될 때의 동작
            selectedDate = formatSelectedDate(year, month, dayOfMonth)
            selectedDateTextView.text = selectedDate
            if (selectedDate.isNotEmpty()) {
                cancel.visibility = View.VISIBLE
            } else {
                cancel.visibility = View.GONE
            }
            updateRegisterButtonImage()
        }

        cancel.setOnClickListener {
            // 캘린더의 선택된 날짜를 초기화합니다.
            calendarView.date = System.currentTimeMillis()
            // 선택된 날짜 텍스트를 초기화합니다.
            selectedDateTextView.text = "이사 일정을 선택해 주세요"
            // 취소 버튼을 숨깁니다.
            cancel.visibility = View.GONE
            // 등록 버튼 이미지를 업데이트합니다.
            registerButton.setImageResource(R.drawable.btn_register)
            // 등록 버튼 활성화 상태로 설정합니다.
            registerButton.isEnabled = false
        }

        // 이미지 버튼 클릭 시 동작
        registerButton.setOnClickListener {
            // 선택한 날짜를 IsaScheduleActivity로 전달
            val intent = Intent(this, IsaScheduleActivity::class.java)
            intent.putExtra("selectedDate", selectedDate)
            startActivity(intent)

            /*val call = RetrofitObject.getRetrofitService.schedule("Bearer $token")

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
                            intent.putExtra("selectedDate", selectedDate)
                            startActivity(intent)
                            val stackBuilder = TaskStackBuilder.create(this@ScheduleActivity)
                            stackBuilder.addNextIntentWithParentStack(intent)
                            stackBuilder.startActivities()
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
            })*/
        }

        // imageView10을 클릭했을 때 액티비티 종료
        val imageView10: ImageView = findViewById(R.id.imageView10)
        imageView10.setOnClickListener {
            finish()
        }

        // 년도 텍스트뷰 클릭 시 bottom sheet 표시
        val yearTextView: TextView = findViewById(R.id.year_text)
        yearTextView.setOnClickListener {
            showBottomSheetCalendar()
        }

    }

    private fun formatSelectedDate(year: Int, month: Int, dayOfMonth: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

    private fun updateRegisterButtonImage() {
        // 선택된 날짜가 있을 때만 버튼을 활성화
        registerButton.isEnabled = true
        // 아무 날짜가 선택되었을 때 항상 btn_register_blue.xml로 버튼 이미지 변경
        registerButton.setImageResource(R.drawable.btn_register_blue)
    }

    private fun showBottomSheetCalendar() {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = LayoutInflater.from(this).inflate(R.layout.bottomsheet_calendar, null)

        // 1월 TextView를 찾습니다.
        val month1TextView: TextView = bottomSheetView.findViewById(R.id.month1)
        // month1을 클릭했을 때의 동작을 정의합니다.
        month1TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 1월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JANUARY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 2월 TextView를 찾습니다.
        val month2TextView: TextView = bottomSheetView.findViewById(R.id.month2)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month2TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 2월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.FEBRUARY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 3월 TextView를 찾습니다.
        val month3TextView: TextView = bottomSheetView.findViewById(R.id.month3)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month3TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 3월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.MARCH, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 4월 TextView를 찾습니다.
        val month4TextView: TextView = bottomSheetView.findViewById(R.id.month4)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month4TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 4월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.APRIL, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 5월 TextView를 찾습니다.
        val month5TextView: TextView = bottomSheetView.findViewById(R.id.month5)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month5TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 5월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.MAY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 6월 TextView를 찾습니다.
        val month6TextView: TextView = bottomSheetView.findViewById(R.id.month6)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month6TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 6월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JUNE, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 7월 TextView를 찾습니다.
        val month7TextView: TextView = bottomSheetView.findViewById(R.id.month7)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month7TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 7월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.JULY, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 8월 TextView를 찾습니다.
        val month8TextView: TextView = bottomSheetView.findViewById(R.id.month8)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month8TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 8월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.AUGUST, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 9월 TextView를 찾습니다.
        val month9TextView: TextView = bottomSheetView.findViewById(R.id.month9)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month9TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 9월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.SEPTEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 10월 TextView를 찾습니다.
        val month10TextView: TextView = bottomSheetView.findViewById(R.id.month10)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month10TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 10월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.OCTOBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 11월 TextView를 찾습니다.
        val month11TextView: TextView = bottomSheetView.findViewById(R.id.month11)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month11TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 11월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.NOVEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        // 12월 TextView를 찾습니다.
        val month12TextView: TextView = bottomSheetView.findViewById(R.id.month12)
        // month2을 클릭했을 때의 동작을 정의합니다.
        month12TextView.setOnClickListener {
            // 캘린더뷰의 날짜를 2023년 12월로 설정합니다.
            calendarView.date = Calendar.getInstance().apply {
                set(2023, Calendar.DECEMBER, 1)
            }.timeInMillis
            // 바텀 시트를 닫습니다.
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()
    }
}
