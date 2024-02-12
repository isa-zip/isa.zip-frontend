package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Help : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // HelpFragment의 레이아웃을 inflate하여 반환합니다.
        return inflater.inflate(R.layout.fragment_help, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchbar = view.findViewById<RelativeLayout>(R.id.searchbar)
        searchbar.setOnClickListener {
            try {
                // ScheduleActivity 화면 전환
                // SearchLocationActivity3 원래 이거임!!!!!!!!!
                val intent = Intent(activity, SearchLocationActivity3::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("HelpFragment", "com.example.zipfront.ScheduleActivity 전환 중 오류 발생: ${e.message}")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Help().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}