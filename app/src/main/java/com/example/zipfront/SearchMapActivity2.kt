package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.example.zipfront.databinding.ActivitySearchmap2Binding
import com.example.zipfront.databinding.ActivitySearchmapBinding
import net.daum.mf.map.api.MapView

class SearchMapActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySearchmap2Binding
    val REQUEST_CODE_OPTION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchmap2Binding.inflate(layoutInflater)

        //editText text 바꾸기
        val locationData = intent.getStringExtra("location")
        binding.searchEt.setText(locationData)

        setContentView(binding.root)


        binding.leftImage.setOnClickListener {
            finish()
        }

        binding.deleteSearchIv.setOnClickListener {
            finish()
        }

        binding.imageButton11.setOnClickListener {
            val intent = Intent(this, MatchingOptionActivity::class.java)
//            startActivityForResult(intent, REQUEST_CODE_OPTION)
            startActivity(intent)
        }


        //mapV1
        val mapView = MapView(this)
        val mapViewContainer = binding.layout1 as ViewGroup
        mapViewContainer.addView(mapView)


        /*//Map
        val mapView : MapView = binding.mapView

        mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
            }
        })*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_OPTION && resultCode == Activity.RESULT_OK) {
            // Handle the result from MatchingOptionActivity if needed
            val customData = data?.getStringExtra("EXTRA_CUSTOM_DATA")
            Log.d(
                "SearchMapT2",
                "Received custom data from MatchingOptionActivity: $customData"
            )
        }
    }
}