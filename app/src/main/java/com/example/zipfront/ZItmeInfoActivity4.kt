package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.ActivityPropertyinfoBinding

class ZItmeInfoActivity4:AppCompatActivity() {
    lateinit var binding: ActivityPropertyinfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyinfoBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction().replace(binding.propertyInfoFrame.id, PropertyInfoFragment()).commitAllowingStateLoss()

        //전화하기 버튼 클릭시 bottom sheet
        binding.callBtn.setOnClickListener(){
            val bottomSheetFragment = CallBottomsheetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)

        }

        //문자하기 버튼 클릭시 바텀 bottom sheet
        binding.messageBtn.setOnClickListener(){
            val bottomSheetFragment = MessageBottomsheetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        //뒤로가기 버튼
        binding.leftImage.setOnClickListener(){
            finish()
        }

        setContentView(binding.root)
    }
}