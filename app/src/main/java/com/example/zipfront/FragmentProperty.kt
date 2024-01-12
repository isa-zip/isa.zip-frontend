package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.PropertyFragmentBinding

class FragmentProperty: Fragment() {
    lateinit var binding: PropertyFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PropertyFragmentBinding.inflate(inflater, container, false)

        // 텍스트 파란색으로 변환
        val textData: String = binding.propertyNum.text.toString()
        val builder = SpannableStringBuilder(textData)

        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zipblue01))
        builder.setSpan(colorBlueSpan, 0, findTextIndex(textData), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.propertyNum.text = builder
        //


        //edittext 클릭시
        binding.searchEt.setOnClickListener {
            //검색 처리 코드
        }
        //


        //리사이클러뷰
        val list = ArrayList<PropertyData>()
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 000/00", "00.00m 옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))



        binding.propertyRv.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyRv.adapter = PropertyAdapter(list)
        binding.propertyRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))


        return binding.root
    }

    fun findTextIndex(textData: String): Int {
        val targetIndex = textData.indexOf("개")
        return if (targetIndex != -1) {
            targetIndex + 1
        } else {
            2
        }
    }


}
