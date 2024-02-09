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

class IsaScheduleBottomSheet() : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.bottomsheet_dialog_component, container, false)
        val btnOK: ImageButton = view.findViewById(R.id.imageButton4)
        val btnClose: ImageButton = view.findViewById(R.id.imageButton5)

        btnOK.setOnClickListener {
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }

        return view
    }

}