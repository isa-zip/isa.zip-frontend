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

class MatchingBottomsheetFragment(context: Context, private val matchingAdapter: MatchingAdapter) : BottomSheetDialogFragment() {

    private val mContext: Context = context
    val REQUEST_CODE_OPTION = 1
    private val REQUEST_CODE_CLOSE = 2
    private lateinit var requestOptionLauncher: ActivityResultLauncher<Intent>
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.matchingbottomsheetdialog, container, false)
        val btnOK: ImageButton = view.findViewById(R.id.imageButton4)
        val btnClose: ImageButton = view.findViewById(R.id.imageButton5)

        btnOK.setOnClickListener {
            val intent = Intent(requireContext(), MatchingOptionActivity::class.java)
            requestOptionLauncher.launch(intent)
            (activity as? MatchingActivity)?.updateFragmentAndViewPager()
            dismiss()
        }

        btnClose.setOnClickListener {
            (activity as? MatchingActivity)?.closeBottomSheetWithCode(REQUEST_CODE_CLOSE)
            dismiss()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // registerForActivityResult를 onViewCreated에서 호출
        requestOptionLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                // 이곳에서 onActivityResult와 동일한 작업 수행
                val resultCode = result.resultCode
                val data = result.data

                Log.d(
                    "MatchingStillFragment3",
                    "requestCode: $REQUEST_CODE_OPTION, resultCode: $resultCode"
                )

                if (resultCode == Activity.RESULT_OK) {
                    // MatchingOptionActivity에서 돌아왔을 때의 처리
                    // 여기서 필요한 작업을 수행합니다.
                    val fragment = matchingAdapter.fragments[viewPager.currentItem]

                    if (fragment is MatchingStillFragment) {
                        fragment.onActivityResult(
                            REQUEST_CODE_OPTION,
                            resultCode,
                            data
                        )
                    }
                }
            }
    }
}