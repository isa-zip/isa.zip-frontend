package com.example.zipfront

import com.example.zipfront.Help
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.zipfront.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.util.maps.helper.Utility
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.AmainBnv.setItemIconTintList(null);
        binding.imageView5.setOnClickListener {
            val intent = Intent(this, MenuFirstActivity::class.java)
            startActivity(intent)
        }
        val fragmentToLoad = intent.getStringExtra("fragmentToLoad")
        Log.d("확인", "$fragmentToLoad")
        //val fragmentToLoad = intent.getStringExtra("fragmentToLoad")
        //Log.d("프래그먼트", "$fragmentToLoad")
        initBottomNavigation(fragmentToLoad)
        //getKakaoMapHashKey(this)

    }
    private fun initBottomNavigation(fragmentToLoad : String?) {

        bottomNavigationView = binding.AmainBnv

        if (fragmentToLoad.isNullOrEmpty()) {
            binding.AmainToolbar.visibility = View.VISIBLE
            bottomNavigationView.selectedItemId = R.id.homeFragment
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, FragmentHome())
                .commitAllowingStateLoss()
        }
        else if(fragmentToLoad == "propertyFragment") {
            binding.AmainToolbar.visibility = View.VISIBLE
            bottomNavigationView.selectedItemId = R.id.propertyFragment
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, FragmentProperty())
                .commitAllowingStateLoss()
        }

        //bottomNavigationView.selectedItemId = R.id.homeFragment

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.propertyFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentProperty()) // Replace with your fragment
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.homeFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, FragmentHome())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.helpFragment -> {
                    binding.AmainToolbar.visibility = View.VISIBLE
                    supportFragmentManager
                        .beginTransaction()
                        .replace(binding.AmainFrame.id, Help())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }




    // PropertyFragment를 보여주는 메서드
    private fun showPropertyFragment(fragmentToLoad : String) {
        if (fragmentToLoad == "propertyFragment") {
            supportFragmentManager
                .beginTransaction()
                .replace(binding.AmainFrame.id, FragmentHome())
                .commitAllowingStateLoss()
        }
    }




    //카카오맵 해시 키 구하기
        /*fun getKakaoMapHashKey(context: Context) {
            try {
                val packageName = context.packageName
                val packageInfo = context.packageManager.getPackageInfo(
                    packageName,
                    PackageManager.GET_SIGNATURES
                )
                for (signature in packageInfo.signatures) {
                    val md = MessageDigest.getInstance("SHA")
                    md.update(signature.toByteArray())
                    val hashKey = Base64.encodeToString(md.digest(), Base64.NO_WRAP)
                    Log.d("KakaoMap Hash Key", hashKey)
                }
            } catch (e: Exception) {
                Log.e("KakaoMap Hash Key", "Error: ${e.message}")
            }
        }*/
}