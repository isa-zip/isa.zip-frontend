package com.example.zipfront

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.ActivityManagementinfoBinding

class ManagementInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityManagementinfoBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManagementinfoBinding.inflate(layoutInflater)
        //brokerItemId 받아오기
        val intent = intent
        val brokerItemId = intent.getIntExtra("brokerItemID", 1)

        //brokerItemId 보내기 (PropertyInfoFragment.kt로)
        val fragment = PropertyInfoFragment.newInstance(brokerItemId)
        // PropertyInfoFragment 띄우기
        supportFragmentManager.beginTransaction().replace(binding.propertyInfoFrame.id, fragment).commitAllowingStateLoss()



        //케밥메뉴 선택시
        binding.buttonSetting.setOnClickListener(){
            val bottomSheetFragment = ManagementBottomsheetDialogFragment(brokerItemId)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        binding.leftImage.setOnClickListener {
            finish()
        }

        setContentView(binding.root)
    }
}