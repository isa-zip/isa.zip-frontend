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

class MatchingStillFragment : Fragment() {
    lateinit var binding: MatchingstillRecyclerviewBinding
    private lateinit var adapter: OuteroptionAdapter
    private lateinit var outerItemList: List<OuterItem>
    val REQUEST_CODE_OPTION = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= MatchingstillRecyclerviewBinding.inflate(inflater,container,false)

        binding.textView10.visibility = View.VISIBLE
        binding.notShowing2.visibility = View.GONE

        outerItemList = listOf(
            OuterItem("상도동", listOf("내부 아이템 1-1", "내부 아이템 1-2", "내부 아이템 1-3")),
            OuterItem("동선동", listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")),
            OuterItem("목동"),
            OuterItem("성신여대", listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4","내부 아이템 4-5"))
        )

        setupRecyclerView(outerItemList)

        // OuterItem의 개수를 textView20에 설정
        binding.textView20.text = "${outerItemList.size}개"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰가 생성된 후에 레이아웃 상태를 설정
        // 여기에서 onActivityResult를 직접 호출하지 않습니다.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("MatchingStillFragment", "requestCode: $requestCode, resultCode: $resultCode")

        if (requestCode == REQUEST_CODE_OPTION && resultCode == Activity.RESULT_OK) {
            binding.textView10.visibility = View.GONE
            binding.notShowing2.visibility = View.VISIBLE
        } else {
            binding.textView10.visibility = View.VISIBLE
            binding.notShowing2.visibility = View.GONE
        }
    }


    private fun setupRecyclerView(outerItemList: List<OuterItem>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuteroptionAdapter(outerItemList)
        binding.optionRv.adapter = adapter
    }

    data class OuterItem(val title: String, val innerItemList: List<String>? = null) {
        fun getItemCount(): Int {
            return innerItemList?.size ?: 0
        }
    }

}