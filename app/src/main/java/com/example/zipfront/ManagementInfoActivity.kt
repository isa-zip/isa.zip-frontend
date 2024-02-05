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
        // PropertyInfoFragment 띄우기
        supportFragmentManager.beginTransaction().replace(binding.propertyInfoFrame.id, PropertyInfoFragment()).commitAllowingStateLoss()

        setContentView(binding.root)

        //케밥메뉴 선택시
        binding.buttonSetting.setOnClickListener(){
            val bottomSheetFragment = ManagementBottomsheetDialogFragment()
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        binding.leftImage.setOnClickListener {
            finish()
        }
    }
}