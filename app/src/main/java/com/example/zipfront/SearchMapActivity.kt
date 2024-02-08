package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.databinding.ActivitySearchmapBinding
import net.daum.mf.map.api.MapView

class SearchMapActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchmapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchmapBinding.inflate(layoutInflater)

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

        binding.choiceButton.setOnClickListener {
            val parentView = binding.parentLayout
            parentView.removeView(binding.mapView)
            //binding.mapView.setOnClickListener(null)
            //binding.mapView.setOnLongClickListener(null)
            //binding.mapView.onSurfaceDestroyed()
            val intent = Intent(this@SearchMapActivity, MainActivity::class.java)
            intent.putExtra("fragmentToLoad", "propertyFragment")
            startActivity(intent)
            finish()
        }



        //mapV1



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

}