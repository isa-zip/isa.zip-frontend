package com.example.zipfront

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment

class LocationPermissionFragment : DialogFragment() {

    private val ACCESS_FINE_LOCATION = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.location_permission_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton : ImageButton = view.findViewById(R.id.imageButton1)
        cancelButton.setOnClickListener {
            // 취소 버튼 클릭 시 다이얼로그 닫기
            dismiss()
        }

        val confirmButton: ImageButton = view.findViewById(R.id.imageButton2)
        confirmButton.setOnClickListener {
            // 확인 버튼 클릭 시 권한 요청
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
            dismiss()
        }
    }

}