package com.example.zipfront

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageBottomsheetDialogFragment() : BottomSheetDialogFragment() {
    val telNum = "010-1234-5678"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.messagebottomsheetdialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view.findViewById(R.id.phone_number_btn)
        button.text = telNum
        button.setOnClickListener() {
            requireContext()!!.startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse("smsto:${button.text}"))
            )
        }
    }
}

