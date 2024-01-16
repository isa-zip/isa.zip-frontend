package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.MatchingstillLayoutBinding
import com.example.zipfront.databinding.MatchingstillRecyclerviewBinding
import com.example.zipfront.databinding.MatchingstilluploadRecyclerviewBinding

class MatchingStillUploadFragment : Fragment() {
    lateinit var binding: MatchingstilluploadRecyclerviewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MatchingstilluploadRecyclerviewBinding.inflate(inflater,container,false)

        binding.textView10.visibility = View.VISIBLE
        binding.notShowing2.visibility = View.GONE

        return binding.root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // arguments에서 requestCode 값을 읽어옵니다.
        val requestCode = arguments?.getInt("requestCode", -1) ?: -1
        Log.d("MatchingStillUpload", "Received requestCode: $requestCode")

        // TODO: requestCode에 따른 추가적인 처리를 수행하세요.
    }

    companion object {
        // newInstance 메서드를 통해 Fragment에 필요한 매개변수 전달
        fun newInstance(requestCode: Int): MatchingStillUploadFragment {
            val fragment = MatchingStillUploadFragment()
            val args = Bundle()
            args.putInt("requestCode", requestCode)
            fragment.arguments = args
            return fragment
        }
    }
}