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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.databinding.MatchingOptionBinding
import com.example.zipfront.databinding.UploadbottomsheetdialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadBottomsheetFragment() : BottomSheetDialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BottomSheetAdapter
    private lateinit var btnClose: ImageButton
    lateinit var binding: UploadbottomsheetdialogBinding

    // 선택된 아이템을 반환하는 메소드
    fun getSelectedItem(): String? {
        return adapter.getSelectedItem()
    }

    private fun getBinding(view: View): UploadbottomsheetdialogBinding {
        return UploadbottomsheetdialogBinding.bind(view)
    }

    // Adapter에서 호출할 메소드
    fun updateBtnCloseBackground() {
        Log.d("UpdateBtnCloseBackground", "Method called")
        val selectedItem = getSelectedItem()
        Log.d("SelectedItem", "Selected Item: $selectedItem")

        activity?.runOnUiThread {
            if (selectedItem != null) {
                binding.imageButton5.setBackgroundResource(R.drawable.btn_agent_blue)
            } else {
                binding.imageButton5.setBackgroundResource(R.drawable.btn_agent_gray)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.uploadbottomsheetdialog, container, false)
        binding = getBinding(view)
        val btnOK: ImageButton = view.findViewById(R.id.imageButton4)
        btnClose = view.findViewById(R.id.imageButton5)

        recyclerView = view.findViewById(R.id.option_rv)
        adapter = BottomSheetAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        updateBtnCloseBackground()

        btnOK.setOnClickListener {
            dismiss()
        }

        btnClose.setOnClickListener {
            dismiss()
        }

        return view
    }
}