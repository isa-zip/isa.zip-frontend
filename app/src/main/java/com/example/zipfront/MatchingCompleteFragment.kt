package com.example.zipfront

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.MatchingcompleteRecyclerviewBinding

class MatchingCompleteFragment: Fragment() {
    lateinit var binding: MatchingcompleteRecyclerviewBinding
    private lateinit var outerItemList: List<OuterItem2>
    private lateinit var adapter: OuterCompleteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MatchingcompleteRecyclerviewBinding.inflate(inflater, container, false)

        outerItemList = listOf(
            OuterItem2(
                "성수동",
                listOf("숭실대입구 4번출구 바로 앞 가성비 좋지않냐? 아닌가? 나도 모름", "내부 아이템 1-2", "내부 아이템 1-3")
            ),
            OuterItem2(
                "동선동",
                listOf("내부 아이템 2-1", "내부 아이템 2-2", "내부 아이템 2-3", "내부 아이템 2-4")
            ),
            OuterItem2("목동"),
            OuterItem2(
                "성신여대",
                listOf("내부 아이템 4-1", "내부 아이템 4-2", "내부 아이템 4-3", "내부 아이템 4-4", "내부 아이템 4-5")
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView(outerItemList)
    }

    private fun setupRecyclerView(outerItemList: List<OuterItem2>) {
        // RecyclerView의 레이아웃 매니저 설정
        binding.optionRv.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)

        // RecyclerView의 어댑터 설정
        adapter = OuterCompleteAdapter(outerItemList)
        binding.optionRv.adapter = adapter
    }
    data class OuterItem2(val title: String, val innerItemList: List<String>? = null) {
        fun getItemCount(): Int {
            return innerItemList?.size ?: 0
        }
    }
}