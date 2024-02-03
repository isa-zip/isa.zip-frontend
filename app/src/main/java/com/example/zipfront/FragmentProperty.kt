package com.example.zipfront

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.databinding.PropertyFragmentBinding
import net.daum.mf.map.api.MapView


class FragmentProperty: Fragment() {
    private lateinit var binding: PropertyFragmentBinding
    private var initialY = 0f

    private val ACCESS_FINE_LOCATION = 1000


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PropertyFragmentBinding.inflate(inflater, container, false)



        //mapV1
/*        val mapView = MapView(requireContext())
        val mapViewContainer = binding.layout1 as ViewGroup
        mapViewContainer.addView(mapView)*/

        //mapV2
        /*mapView.start(object : MapLifeCycleCallback() {
            override fun onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.d("MapActivity1", "onMapDestroy called")
            }

            override fun onMapError(error: Exception) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.e("MapActivity2", "onMapError called with error: $error")
            }
        }, object : KakaoMapReadyCallback() {
            override fun onMapReady(kakaoMap: KakaoMap) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                Log.d("MapActivity3", "onMapReady called")
            }
        })*/

        //현재위치
        // 위치추적 버튼
        binding.currentLocationBtn.setOnClickListener {
            if (checkLocationService()) {
                // GPS가 켜져있을 경우
                permissionCheck()
                Toast.makeText(requireContext(), "GPS가 켜져있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // GPS가 꺼져있을 경우
                Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }


        //지도 검색했을 때 검색 액티비티로 넘어가도록
        binding.searchImage.setOnClickListener {
            val intent = Intent(requireContext(), SearchLocationActivity::class.java)
            startActivity(intent)

        }

        //필터적용
        binding.filterImage.setOnClickListener {
            val intent = Intent(requireContext(), OptionActivity::class.java)
            startActivity(intent)
        }





        // 텍스트 파란색으로 변환
        val textData: String = binding.propertyNum.text.toString()
        val builder = SpannableStringBuilder(textData)

        val colorBlueSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.zipblue01))
        builder.setSpan(colorBlueSpan, 0, findTextIndex(textData), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.propertyNum.text = builder
        //



        //리사이클러뷰
        val list = ArrayList<PropertyData>()
        list.add(PropertyData(R.drawable.img, "월세 001/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 002/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 003/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 004/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 005/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 006/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 007/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 008/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 009/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))
        list.add(PropertyData(R.drawable.img, "월세 010/00", "00.00m", "옥탑방", "동작구 상도동", "설명을 작성해주세요. 설명을 작성해 주.."))

        binding.propertyRv.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyRv.adapter = PropertyAdapter(list)
        binding.propertyRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))


        //터치 이벤트 처리
        binding.statusImage.setOnTouchListener { v: View, event: MotionEvent ->
            if (isTouchInsideView(event, binding.searchToolbar, binding.mapView)) {
                true // 이벤트 소비
            } else {
                when (event.action) {
                    //화면에 손가락이 닿았을 때
                    MotionEvent.ACTION_DOWN -> {
                        initialY = event.y
                    }
                    //손가락을 이동시킬 때
                    MotionEvent.ACTION_MOVE -> {
                        val deltaY = event.y - initialY

                        //화면을 위로 스와이프 했을 때의 동작
                        if (deltaY < 0) {
                            moveLayout(deltaY)
                        }
                        //화면을 아래로 스와이프 했을 때의 동작
                        if (deltaY > 0) {
                            moveLayout(deltaY)
                        }
                    }

                    MotionEvent.ACTION_UP -> {
                        //y좌표 초기화
                        initialY = 0f
                    }
                }
                true
            }
        }


        return binding.root
    }

    //글씨 파란색으로 바꿀 때 인덱스 찾는 함수
    fun findTextIndex(textData: String): Int {
        val targetIndex = textData.indexOf("개")
        return if (targetIndex != -1) {
            targetIndex + 1
        } else {
            2
        }
    }

    //매물 리스트 올리기
    private fun moveLayout(deltaY: Float) {
        val layoutParams = binding.layoutConstraint.layoutParams as ConstraintLayout.LayoutParams
        val rvLayoutParams = binding.propertyRv.layoutParams
        //올리기
        layoutParams.topMargin += deltaY.toInt()
        binding.layoutConstraint.layoutParams = layoutParams
        //리사이클러뷰 크기 조정
        rvLayoutParams.height -= deltaY.toInt()
        binding.propertyRv.layoutParams = rvLayoutParams
    }

    private fun isTouchInsideView(event: MotionEvent, view1: View, view2: View): Boolean {
        val y = event.rawY.toInt()
        val location1 = IntArray(2)
        view1.getLocationOnScreen(location1)
        val view1Y = location1[1]
        val location2 = IntArray(2)
        view2.getLocationOnScreen(location1)
        val view2Y = location1[1]


        return (y < view1Y && y < view2Y)
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    // 위치 권한 확인
    private fun permissionCheck() {
        val preference = requireContext().getSharedPreferences("isFirstPermissionCheck", Context.MODE_PRIVATE)
        val isFirstCheck = preference.getBoolean("isFirstPermissionCheck", true)
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 상태
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 권한 거절 (다시 한 번 물어봄)
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->

                }
                builder.show()
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.zipfront"))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->

                    }
                    builder.show()
                }
            }
        } else {
            // 권한이 있는 상태
            startTracking()
        }
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }



    // 권한 요청 후 행동
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한 요청 후 승인됨 (추적 시작)
                Toast.makeText(requireContext(), "위치 권한이 승인되었습니다", Toast.LENGTH_SHORT).show()
                startTracking()
            } else {
                // 권한 요청 후 거절됨 (다시 요청 or 토스트)
                Toast.makeText(requireContext(), "위치 권한이 거절되었습니다", Toast.LENGTH_SHORT).show()
                permissionCheck()
            }
        }


    }

}
