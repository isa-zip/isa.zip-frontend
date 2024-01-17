package com.example.zipfront

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zipfront.databinding.PropertyFragmentBinding

class FragmentProperty: Fragment() {
    lateinit var binding: PropertyFragmentBinding
    private var initialY = 0f
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PropertyFragmentBinding.inflate(inflater, container, false)

        //지도 검색했을 때 검색 액티비티로 넘어가도록
        binding.searchImage.setOnClickListener {
            val intent = Intent(requireContext(), SearchLocationActivity::class.java)
            startActivity(intent)

        }





        // 텍스트 파란색으로 변환
        val textData: String = binding.propertyNum.text.toString()
        val builder = SpannableStringBuilder(textData)

        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zipblue01))
        builder.setSpan(colorBlueSpan, 0, findTextIndex(textData), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.propertyNum.text = builder
        //



        //리사이클러뷰
        val list = ArrayList<PropertyData>()
        list.add(PropertyData(R.drawable.img, "월세 001/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 002/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 003/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 004/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 005/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 006/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 007/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 008/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 009/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 010/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))

        binding.propertyRv.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyRv.adapter = PropertyAdapter(list)
        binding.propertyRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        //리사이클러뷰 아이템 클릭시
        var propertyAdapter = PropertyAdapter(list)


        //터치 이벤트 처리
        binding.statusImage.setOnTouchListener { v: View, event: MotionEvent ->
            if (isTouchInsideView(event, binding.searchToolbar, binding.mapFragment)) {
                true // 이벤트 소비
            } else {
                when (event.action) {
                    //화면에 손가락이 닿았을 때
                    MotionEvent.ACTION_DOWN -> {
                        initialY = event.y
                    }
                    //손가락을 이동시킬 때
                    MotionEvent.ACTION_MOVE -> {
                        val deltaY = event.y - initialY

                        //화면을 위로 스와이프 했을 때의 동작
                        if (deltaY < 0) {
                            moveLayout(deltaY)
                        }
                        //화면을 아래로 스와이프 했을 때의 동작
                        if (deltaY > 0) {
                            moveLayout(deltaY)
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        //y좌표 초기화
                        initialY = 0f
                    }
                }
                true
            }
        }


        return binding.root
    }

    //글씨 파란색으로 바꿀 때 인덱스 찾는 함수
    fun findTextIndex(textData: String): Int {
        val targetIndex = textData.indexOf("개")
        return if (targetIndex != -1) {
            targetIndex + 1
        } else {
            2
        }
    }

    //매물 리스트 올리기
    private fun moveLayout(deltaY: Float) {
        val layoutParams = binding.layoutConstraint.layoutParams as ConstraintLayout.LayoutParams
        val rvLayoutParams = binding.propertyRv.layoutParams
        //올리기
        layoutParams.topMargin += deltaY.toInt()
        binding.layoutConstraint.layoutParams = layoutParams
        //리사이클러뷰 크기 조정
        rvLayoutParams.height -= deltaY.toInt()
        binding.propertyRv.layoutParams = rvLayoutParams
    }

    private fun isTouchInsideView(event: MotionEvent, view1: View, view2: View): Boolean {
        val y = event.rawY.toInt()
        val location1 = IntArray(2)
        view1.getLocationOnScreen(location1)
        val view1Y = location1[1]
        val location2 = IntArray(2)
        view2.getLocationOnScreen(location1)
        val view2Y = location1[1]


        return (y < view1Y && y < view2Y)
    }



}
