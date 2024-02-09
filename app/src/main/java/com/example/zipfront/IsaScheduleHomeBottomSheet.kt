// 이사스케줄홈바텀시트
package com.example.zipfront

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class IsaScheduleHomeBottomSheet() : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottomsheet_dialog_component, container, false)
        val btnModify: ImageButton = view.findViewById(R.id.imageButton4)
        val btnDelete: ImageButton = view.findViewById(R.id.imageButton5)

        btnModify.setOnClickListener {
            dismiss() // 현재 다이얼로그를 닫음

            val intent = Intent(activity, ScheduleHomeModifyActivity::class.java)
            startActivity(intent)
        }


        btnDelete.setOnClickListener {
            dismiss()
        }

        return view
    }

}