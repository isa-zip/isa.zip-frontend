package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ManagementBottomsheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.managementbottomsheetdialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button1: Button = view.findViewById(R.id.modify_btn)
        button1.setOnClickListener() {
            //수정하기
        }

        val button2: Button = view.findViewById(R.id.sold_out_btn)
        button2.setOnClickListener() {
            showSoldOutDialog()
        }

        val button3: Button = view.findViewById(R.id.delete_btn)
        button3.setOnClickListener() {
            showDeleteDialog()
        }

    }

    private fun showSoldOutDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.soldout_dialog, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val params =  alertDialog.window?.attributes
        params?.width = 296
        params?.height = 149
        alertDialog.window?.attributes = params

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.cancel_btn)
        val deleteButton = dialogView.findViewById<ImageButton>(R.id.finish_btn)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        // 확인 버튼 클릭 리스너 설정
        deleteButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        alertDialog.show()
    }


    private fun showDeleteDialog() {
        // 다이얼로그 레이아웃을 inflate
        val dialogView = layoutInflater.inflate(R.layout.delete_dialog, null)

        // AlertDialog.Builder를 사용하여 다이얼로그 생성
        val builder = AlertDialog.Builder(requireContext())
        builder.setView(dialogView)
        // AlertDialog 생성
        val alertDialog: AlertDialog = builder.create()

        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Window 속성을 사용하여 크기 조절
        val params =  alertDialog.window?.attributes
        params?.width = 296
        params?.height = 149
        alertDialog.window?.attributes = params

        // 다이얼로그 내부의 ImageButton 참조
        val cancelButton = dialogView.findViewById<ImageButton>(R.id.cancel_btn)
        val deleteButton = dialogView.findViewById<ImageButton>(R.id.delete_btn)

        // 취소 버튼 클릭 리스너 설정
        cancelButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        // 확인 버튼 클릭 리스너 설정
        deleteButton.setOnClickListener {
            alertDialog.dismiss() // 다이얼로그 닫기
        }

        alertDialog.show()
    }
}

