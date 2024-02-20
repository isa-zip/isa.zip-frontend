package com.example.zipfront

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.contains
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zipfront.connection.RetrofitClient2
import com.example.zipfront.databinding.PropertyFragmentBinding
import com.google.gson.annotations.SerializedName
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FragmentProperty: Fragment() {
    private lateinit var binding: PropertyFragmentBinding
    private var initialY = 0f

    private val user = MyApplication.getUser()
    private val token = user.getString("jwt", "").toString()

    private val ACCESS_FINE_LOCATION = 1000
    private lateinit var mapView: MapView
    var mapViewContainer: FrameLayout? = null
    private var mapData = ArrayList<MapData>()

    //마커 찍기
    private var latitude: Double = 126.89698111457
    private var longitude: Double = 37.5122797138519


    //옵션
    private var charterInfo: RetrofitClient2.CharterInfo? = null
    private var tradingInfo: RetrofitClient2.TradingInfo? = null
    private var monthlyInfo: RetrofitClient2.MonthlyInfo? = null
    private val roomTypeList: MutableList<RetrofitClient2.RoomType> = mutableListOf()
    private val dealTypesList: MutableList<RetrofitClient2.DealType> = mutableListOf()
    private val roomSizeList: MutableList<RetrofitClient2.RoomSize> = mutableListOf()
    private val floorList: MutableList<RetrofitClient2.Floor> = mutableListOf()
    private val managementOptionList: MutableList<RetrofitClient2.ManagementOption> = mutableListOf()
    private val internalFacilityList: MutableList<RetrofitClient2.InternalFacility> = mutableListOf()
    private var approveDate: RetrofitClient2.ApproveDate? = null
    private val extraFilterList: MutableList<RetrofitClient2.ExtraFilter> = mutableListOf()
    private val dealInfoMap = RetrofitClient2.DealInfoMap(charterInfo, tradingInfo, monthlyInfo)

    /*public var optionRequest = RetrofitClient2.RequestLocationFilter(
        roomType = roomTypeList,
        dealTypes = dealTypesList,
        dealInfoMap = dealInfoMap,
        roomSize = roomSizeList,
        floor = floorList,
        managementOption = managementOptionList,
        internalFacility = internalFacilityList,
        approveDate = approveDate,
        extraFilter = extraFilterList
    )*/
    public var optionRequest: RetrofitClient2.RequestLocationFilter? = null

    var info1 : String = ""
    var info2 : String = ""
    var info3 : String = ""
    var info4 : String = ""
    var info5 : String = ""
    var tv : String = ""
    var brokerItemId: Int = 1
    var itemCount = 0

    private var isFirstRun : Boolean = true


    override fun onResume() {
        super.onResume()
        if (mapViewContainer?.contains(mapView)!!) {
            try {
                // 다시 맵뷰 초기화 및 추가
                mapView = MapView(requireContext())
                mapViewContainer = binding.mapView
                mapViewContainer?.addView(mapView)
            } catch (re: RuntimeException) {
            }
        }


        /*if (checkLocationService()) {
            // GPS가 켜져있을 경우
            permissionCheck()
            //Toast.makeText(requireContext(), "GPS가 켜져있습니다.", Toast.LENGTH_SHORT).show()
        } else {
            // GPS가 꺼져있을 경우
            Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = PropertyFragmentBinding.inflate(inflater, container, false)

        mapView = MapView(requireContext())
        mapViewContainer = binding.mapView
        mapViewContainer?.addView(mapView)


        if (checkLocationService() && mapView.currentLocationTrackingMode != MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading) {
            // GPS가 켜져있을 경우
            permissionCheck()
            updateRV()
        } else {
            // GPS가 꺼져있을 경우
            Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
        }
        updateRV()

        arguments?.let {
            latitude = it.getDouble("latitude", 126.89698111457)
            longitude = it.getDouble("longitude", 37.5122797138519)
        }

        updateRV()


        //현재위치
        // 위치추적 버튼
        binding.currentLocationBtn.setOnClickListener {
            if (checkLocationService()) {
                // GPS가 켜져있을 경우
                permissionCheck()
                //Toast.makeText(requireContext(), "GPS가 켜져있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // GPS가 꺼져있을 경우
                Toast.makeText(requireContext(), "GPS를 켜주세요", Toast.LENGTH_SHORT).show()
            }
        }


        //지도 검색했을 때 검색 액티비티로 넘어가도록
        binding.searchImage.setOnClickListener {
            val intent = Intent(requireContext(), SearchLocationActivity::class.java)
            binding.mapView.removeView(binding.mapView)
            startActivity(intent)

        }

        //필터적용
        binding.filterImage.setOnClickListener {
            val intent = Intent(requireContext(), OptionActivity::class.java)
            startActivity(intent)
        }





        //리사이클러뷰

        /*binding.propertyRv.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyRv.adapter = PropertyAdapter(list)
        binding.propertyRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
        */


        /*val request = RetrofitClient2.RequestLocationFilter(
            roomType = roomTypeList,
            dealTypes = dealTypesList,
            dealInfoMap = dealInfoMap,
            roomSize = roomSizeList,
            floor = floorList,
            managementOption = managementOptionList,
            internalFacility = internalFacilityList,
            approveDate = approveDate,
            extraFilter = extraFilterList
        )*/

        val optionActivity = OptionActivity()
        optionRequest = optionActivity.sendData()
        Log.d("확인2", optionRequest.toString())







        //터치 이벤트 처리
        /*binding.statusImage.setOnTouchListener { v: View, event: MotionEvent ->
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
        } */


        return binding.root
    }


    private fun updateRV() {
        val list = ArrayList<PropertyData>()
        val dealInfoMap = RetrofitClient2.DealInfoMap(charterInfo, tradingInfo, monthlyInfo)
        val call = RetrofitObject.getRetrofitService.showPropertyItem("Bearer $token", latitude, longitude)
        call.enqueue(object : Callback<RetrofitClient2.ResponseLocationFilter> {
            override fun onResponse(
                call: Call<RetrofitClient2.ResponseLocationFilter>,
                response: Response<RetrofitClient2.ResponseLocationFilter>
            ) {
                Log.d("Retrofit201", call.toString())
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("Retrofit220", responseBody.toString())
                    if (responseBody != null) {
                        if (responseBody.isSuccess) {
                            val responseData = responseBody.data.brokerItemListList
                            var imgRes: RequestCreator
                            Log.d("Retrofit221", responseData.toString())

                            for (data in responseData) {
                                if (data.itemStatus == "ITEM_SELLING") {
                                    itemCount += 1
                                    //이미지 설정
                                    val imageUrl = data.itemImage.firstOrNull()?.itemImage
                                    imgRes = Picasso.get().load(imageUrl)

                                    //지도 설정
                                    mapData.add(
                                        MapData(
                                            data.brokerItemId,
                                            data.addressName,
                                            data.x,
                                            data.y
                                        )
                                    )
                                    Log.d("mapdata", mapData.toString())
                                    setMissionMark(mapData)

                                    info1 =
                                        if (data.dealTypes.firstOrNull()?.dealType == "CHARTER") {
                                            "전세 ${data.dealTypes.firstOrNull()?.charterPrice ?: "-"}"
                                        } else if (data.dealTypes.firstOrNull()?.dealType == "TRADING") {
                                            "매매 ${data.dealTypes.firstOrNull()?.tradingPrice ?: "-"}"
                                        } else if (data.dealTypes.firstOrNull()?.dealType == "MONTHLY") {
                                            "월세 ${data.dealTypes.firstOrNull()?.monthPrice ?: "-"}"
                                        } else {
                                            "월세 000/00"
                                        }
                                    info2 = "${translateToKorean(data.roomSize)}  "
                                    info3 = "${data.floors.firstOrNull()?.customFloor}"
                                    info4 = data.addressName
                                    tv = data.shortIntroduction
                                    brokerItemId = data.brokerItemId

                                    //리스트에 추가
                                    Log.d("list", list.toString())
                                    list.add(
                                        PropertyData(
                                            imgRes,
                                            info1,
                                            info2,
                                            info3,
                                            info4,
                                            tv,
                                            brokerItemId
                                        )
                                    )

                                }
                            }
                            bindingRV(list)
                            binding.propertyNumber.text = "${itemCount}개"
                        } else {
                            Toast.makeText(
                                requireContext(),
                                responseBody?.message ?: "Unknown error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<RetrofitClient2.ResponseLocationFilter>,
                t: Throwable
            ) {
                val errorMessage = "Call Failed: ${t.message}"
                Log.d("Retrofit22", errorMessage)
            }
        })
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
                /*val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("현재 위치를 확인하시려면 위치 권한을 허용해주세요.")
                builder.setPositiveButton("확인") { dialog, which ->
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                }
                builder.setNegativeButton("취소") { dialog, which ->
                }
                builder.show()*/
                val locationPermissionFragment = LocationPermissionFragment()
                locationPermissionFragment.show(parentFragmentManager, "LocationPermissionFragment")
            } else {
                if (isFirstCheck) {
                    // 최초 권한 요청
                    preference.edit().putBoolean("isFirstPermissionCheck", false).apply()
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION)
                } else {
                    // 다시 묻지 않음 클릭 (앱 정보 화면으로 이동)
                    /*val builder = AlertDialog.Builder(requireContext())
                    builder.setMessage("현재 위치를 확인하시려면 설정에서 위치 권한을 허용해주세요.")
                    builder.setPositiveButton("설정으로 이동") { dialog, which ->
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:com.example.zipfront"))
                        startActivity(intent)
                    }
                    builder.setNegativeButton("취소") { dialog, which ->
                    }
                    builder.show()*/
                    val locationPermissionFragment = LocationPermissionFragment()
                    locationPermissionFragment.show(parentFragmentManager, "LocationPermissionFragment")
                }
            }
        } else {
            // 권한이 있는 상태
            startTracking()
        }
    }

    // 위치추적 시작
    private fun startTracking() {
        mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
        onCurrentLocationUpdate(mapView, mapView.mapCenterPoint)
    }

    fun onCurrentLocationUpdate(mapView: MapView?, currentLocation: MapPoint) {
        currentLocation?.let { location ->
            longitude = location.mapPointGeoCoord.latitude
            latitude = location.mapPointGeoCoord.longitude
            // 현재 위치의 위도와 경도 값을 사용할 수 있습니다.
            // 이 위치 정보를 필요한 곳에 전달하거나 사용할 수 있습니다.
            Log.d("Current Location", "Latitude: $latitude, Longitude: $longitude")
            updateRV()
        }
    }




    //fun onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters)


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


    private fun translateToKorean(keyword: String?): String {
        return when (keyword) {
            "ONE_ROOM" -> "원룸"
            "TWO_OR_THREEROOM" -> "투룸/쓰리룸"
            "OFFICETELS" -> "오피스텔"
            "APARTMENT" -> "아파트"
            "CHARTER" -> "전세"
            "TRADING" -> "매매"
            "MONTHLY" -> "월세"
            "UNDER_FIVE" -> "~5"
            "TEN" -> "10"
            "TWENTY" -> "20"
            "THIRTY" -> "30"
            "FORTY" -> "40"
            "FIFTY" -> "50"
            "OVER_SIXTY" -> "60"
            "ONE" -> "1층"
            "TWO" -> "2층"
            "THREE" -> "3층"
            "FOUR" -> "4층"
            "FIVE" -> "5층"
            "SIX" -> "6층"
            "SEMI_LAYER" -> "반지층"
            "ROOFTOP" -> "옥탑방"
            "ELECTRONIC_FEE" -> "전기세"
            "GAS_FEE" -> "가스비"
            "INTERNET_FEE" -> "인터넷"
            "PARKING_FEE" -> "주차여부"
            "WATER_FEE" -> "수도세"
            "AIR_CONDITIONER" -> "에어컨"
            "REFRIGERATOR" -> "냉장고"
            "WASHING_MACHINE" -> "세탁기"
            "MICROWAVE" -> "전자레인지"
            "CLOSET" -> "옷장"
            "TABLE" -> "책상"
            "TV" -> "TV"
            "BED" -> "침대"
            "ALL" -> "전체"
            "UNDER_ONE_YEAR" -> "1년 이내"
            "UNDER_FIVE_YEARS" -> "5년 이내"
            "UNDER_TEN_YEARS" -> "10년 이내"
            "UNDER_FIFTEEN_YEARS" -> "15년 이내"
            "OVER_FIFTEEN_YEARS" -> "15년 이상"
            "PARKING" -> "주차장"
            "SHORT_LOAN" -> "단기임대"
            "FULL_OPTION" -> "풀옵션"
            "ELEVATOR" -> "엘리베이터"
            "VERANDA" -> "베란다"
            "SECURITY" -> "보안/안전시설"
            "VR" -> "360°VR"
            "NON_FACE_CONTRACT" -> "비대면계약"
            "UNDER_17" -> "~ 17m²"
            "UNDER_66" -> "33~66m²"
            "UNDER_99" -> "66~99m²"
            "UNDER_132" -> "99~132m²"
            "UNDER_165" -> "132~165m²"
            "UNDER_198" -> "165~198m²"
            "OVER_198" -> "198m² ~"
            else -> keyword ?: ""
        }
    }

    private fun bindingRV(list : ArrayList<PropertyData>) {
        binding.propertyRv.layoutManager = LinearLayoutManager(requireContext())
        binding.propertyRv.adapter = PropertyAdapter(list)
        binding.propertyRv.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

    }

    //마커추가
    private fun setMissionMark(dataList: ArrayList<MapData>) {
        for (data in dataList) {
            Log.d("gggg", data.toString())
            val marker = MapPOIItem()
            marker.itemName = data.locationName
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(data.longitude, data.latitude)
            marker.markerType = MapPOIItem.MarkerType.BluePin
            mapView.addPOIItem(marker)
        }
    }

}