package com.example.zipfront

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.ActivitySearchlocationBinding
import com.example.zipfront.databinding.ActivitySearchmap2Binding
import com.example.zipfront.databinding.ActivitySearchmapBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchMapActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivitySearchmap2Binding
    private val API_KEY = "KakaoAK 752d895cabf8520cd29b4cd34878496f"
    val REQUEST_CODE_OPTION = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchmap2Binding.inflate(layoutInflater)

        //editText text 바꾸기
        val locationData = intent.getStringExtra("location") ?: ""
        val locationDong = intent.getStringExtra("dong") ?: ""
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

        binding.imageButton11.setOnClickListener {
            val parentView = binding.parentLayout
            parentView.removeView(binding.mapView)
            Log.d("Retrofitdong2", locationDong.toString())
            val intent = Intent(this, MatchingOptionActivity::class.java)
            intent.putExtra("dong", locationDong)
//            startActivityForResult(intent, REQUEST_CODE_OPTION)
            startActivity(intent)
        }


        //mapV1
        //val mapView = MapView(this)
        //val mapViewContainer = binding.layout1 as ViewGroup
        //mapViewContainer.addView(mapView)


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