package com.example.zipfront

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.zipfront.databinding.ActivityPropertyinfoBinding


class PropertyInfoActivity:AppCompatActivity() {
    lateinit var binding: ActivityPropertyinfoBinding
    var telNum : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyinfoBinding.inflate(layoutInflater)


        //Property Info Fragment 띄우기
        supportFragmentManager.beginTransaction().replace(binding.propertyInfoFrame.id, PropertyInfoFragment()).commitAllowingStateLoss()


        setContentView(binding.root)

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

    }


}