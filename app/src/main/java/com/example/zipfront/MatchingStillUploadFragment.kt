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
//    lateinit var binding: MatchingstilluploadRecyclerviewBinding
//    private lateinit var outeruploadItemList: List<OuterUploadItem>
//    private lateinit var adapter: OuteruploadAdapter
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding= MatchingstilluploadRecyclerviewBinding.inflate(inflater,container,false)
//
//        binding.textView10.visibility = View.GONE
//        binding.notShowing2.visibility = View.VISIBLE
//
//        binding.switch2.isChecked = true
//
//
//        outeruploadItemList = listOf(
//            OuterUploadItem(
//                "상도동",
//                listOf("내부 아이템 1-1", "내부 아이템 1-2", "내부 아이템 1-3")
//            ),
//            OuterUploadItem(
//                "동선동",
//                listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")
//            ),
//            OuterUploadItem("오목교"),
//            OuterUploadItem(
//                "성신여대",
//                listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4", "내부 아이템 4-5")
//            )
//        )
//        setupRecyclerView(outeruploadItemList)
//
//        binding.textView20.text = "${outeruploadItemList.size}개"
//
//        return binding.root
//    }
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        // arguments에서 requestCode 값을 읽어옵니다.
//        val requestCode = arguments?.getInt("requestCode", -1) ?: -1
//        Log.d("MatchingStillUpload", "Received requestCode: $requestCode")
//
//        // TODO: requestCode에 따른 추가적인 처리를 수행하세요.
//    }
//
//    private fun setupRecyclerView(outerItemList: List<OuterUploadItem>) {
//        // RecyclerView의 레이아웃 매니저 설정
//        binding.optionRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//
//        // RecyclerView의 어댑터 설정
//        adapter = OuteruploadAdapter(outerItemList)
//        binding.optionRv.adapter = adapter
//    }
//
//    companion object {
//        // newInstance 메서드를 통해 Fragment에 필요한 매개변수 전달
//        fun newInstance(requestCode: Int): MatchingStillUploadFragment {
//            val fragment = MatchingStillUploadFragment()
//            val args = Bundle()
//            args.putInt("requestCode", requestCode)
//            fragment.arguments = args
//            return fragment
//        }
//    }
//    data class OuterUploadItem(val title: String, val innerItemList: List<String>? = null) {
//        fun getItemCount(): Int {
//            return innerItemList?.size ?: 0
//        }
//    }
}