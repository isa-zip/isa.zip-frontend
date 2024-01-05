package com.example.zipfront

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MatchingBottomsheetFragment(context: Context) : BottomSheetDialogFragment() {

    private val mContext: Context = context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.matchingbottomsheetdialog, container, false)
        val btnOK: ImageButton = view.findViewById(R.id.imageButton4)
        val btnClose: ImageButton = view.findViewById(R.id.imageButton5)

        btnOK.setOnClickListener {
            Toast.makeText(mContext, "확인", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnClose.setOnClickListener {
            Toast.makeText(mContext, "닫기", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        return view
    }
}