package com.example.zipfront

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.example.zipfront.databinding.MatchingActivityBinding

class SearchLocationActivity :AppCompatActivity(){
    lateinit var binding : ActivitySearchlocationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchlocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //자동으로 포커싱, 키보드 올리기
        binding.searchEt.requestFocus()
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        WindowInsetsControllerCompat(window, window.decorView).show(WindowInsetsCompat.Type.ime())
    }
}