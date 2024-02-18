package com.example.zipfront

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivitySearchmapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMapActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchmapBinding
    private val API_KEY = "KakaoAK 752d895cabf8520cd29b4cd34878496f"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchmapBinding.inflate(layoutInflater)

        //editText text 바꾸기
        val locationData = intent.getStringExtra("location") ?: ""
        binding.searchEt.setText(locationData)

        //현재위치 추적모드 끄기
        binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOff

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



        //카카오맵 API연결
        val api = RetrofitKakaoMapObject.getRetrofitService
        val call = api.getSearchKeyword(API_KEY, locationData)
        // API 서버에 요청Callback
        call.enqueue(object : Callback<RetrofitClient2.ResultSearchKeyword> {
            override fun onResponse(call: Call<RetrofitClient2.ResultSearchKeyword>, response: Response<RetrofitClient2.ResultSearchKeyword>) {
                // 통신 성공
                setMap(response.body())
                Log.d("LocalSearch", "${response.body()}")
                Log.d("PlaceMeta", "${response.body()?.meta}")
                Log.d("RegionInfo", "${response.body()?.meta?.same_name}")
                Log.d("Document", "${response.body()?.documents}")
            }
            override fun onFailure(call: Call<RetrofitClient2.ResultSearchKeyword>, t: Throwable) {
                // 통신 실패
                Log.e("LocalSearch", "통신 실패: ${t.message}")
            }
        })

    }

    private fun setMap(searchResult: RetrofitClient2.ResultSearchKeyword?) {
        if (!searchResult?.documents.isNullOrEmpty()) {
        // 검색 결과 있음
            val latitude = searchResult?.documents?.firstOrNull()?.x?.toDouble() ?: 0.0
            val longitude = searchResult?.documents?.firstOrNull()?.y?.toDouble() ?: 0.0
            Log.d("위도 확인", "latitude:${latitude}, longitude:${longitude}")
            binding.mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(longitude, latitude), 4, true);

        //마커 추가
            val marker = MapPOIItem()
            marker.itemName = searchResult?.documents?.firstOrNull()?.address_name
            val mapPoint = MapPoint.mapPointWithGeoCoord(longitude, latitude)
            marker.mapPoint = mapPoint
            marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
            binding.mapView.addPOIItem(marker)


        } else {
        // 검색 결과 없음
            Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
        }
    }

}