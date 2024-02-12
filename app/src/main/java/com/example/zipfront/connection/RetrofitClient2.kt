package com.example.zipfront.connection

import com.google.gson.annotations.SerializedName

class RetrofitClient2 {
    //로그인
    data class Requestlogin(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String
    )

    data class Responselogin(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: LoginResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class LoginResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )

    data class ResponseWithdraw(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ResponseProfilesee(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ProfileseeResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ProfileseeResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("userImg")
        val userImg: String,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("email")
        val email: String
    )

    data class ResponseProfile(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ProfileResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ProfileResult(
        @SerializedName("id")
        val id: String,
        @SerializedName("userImg")
        val userImg: String,
        @SerializedName("nickName")
        val nickName: String,
        @SerializedName("email")
        val email: String
    )

    data class RequestCertify(
        @SerializedName("brokerId")
        val brokerId: Int,
        @SerializedName("name")
        val name: String,
        @SerializedName("phoneNum")
        val phoneNum: String,
        @SerializedName("businessName")
        val businessName: String
    )

    data class ResponseCertify(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )
    // 이메일 회원가입
    data class Requestsetting(
        @SerializedName("email")
        val email: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("nickName")
        val nickName: String
    )

    data class Responsesetting(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ResponseDongcount(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: List<DongcountResult>,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class DongcountResult(
        @SerializedName("dong")
        val dong: String,
        @SerializedName("dongCount")
        val dongCount: Int
    )

    data class RequestUseritem(
        @SerializedName("roomType")
        val roomType: List<RoomType>,
        @SerializedName("dealTypes")
        val dealTypes: List<DealType>,
        @SerializedName("dealInfoMap")
        val dealInfoMap: DealInfoMap,
        @SerializedName("roomSize")
        val roomSize: List<RoomSize>,
        @SerializedName("floor")
        val floor: List<Floor>,
        @SerializedName("managementOption")
        val managementOption: List<ManagementOption>,
        @SerializedName("internalFacility")
        val internalFacility: List<InternalFacility>,
        @SerializedName("approveDate")
        val approveDate: ApproveDate,
        @SerializedName("extraFilter")
        val extraFilter: List<ExtraFilter>
    )

    enum class RoomType {
        ONE_ROOM,
        TWO_OR_THREE_ROOM,
        OFFICETELS,
        APARTMENT
    }

    enum class DealType {
        CHARTER,
        TRADING,
        MONTHLY
    }

    enum class RoomSize {
        UNDER_FIVE,
        TEN,
        TWENTY,
        THIRTY,
        FORTY,
        FIFTY,
        OVER_SIXTY
    }

    enum class Floor {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEMI_LAYER,
        ROOFTOP
    }

    enum class ManagementOption {
        ELECTRONIC_FEE,
        GAS_FEE,
        INTERNET_FEE,
        PARKING_FEE,
        WATER_FEE
    }

    enum class InternalFacility {
        AIR_CONDITIONER,
        REFRIGERATOR,
        WASHING_MACHINE,
        MICROWAVE,
        CLOSET,
        TABLE,
        TV,
        BED
    }

    enum class ApproveDate {
        ALL,
        UNDER_ONE_YEAR,
        UNDER_FIVE_YEARS,
        UNDER_TEN_YEARS,
        UNDER_FIFTEEN_YEARS,
        OVER_FIFTEEN_YEARS
    }

    enum class ExtraFilter {
        PARKING,
        SHORT_LOAN,
        FULL_OPTION,
        ELEVATOR,
        VERANDA,
        SECURITY,
        VR,
        NON_FACE_CONTRACT
    }


    data class DealInfoMap(
        @SerializedName("CHARTER")
        val CHARTER: CharterInfo?,
        @SerializedName("TRADING")
        val TRADING: TradingInfo?,
        @SerializedName("MONTHLY")
        val MONTHLY: MonthlyInfo?
    )

    data class CharterInfo(
        @SerializedName("minPrice")
        val minPrice: Int?,
        @SerializedName("maxPrice")
        val maxPrice: Int?
    )

    data class TradingInfo(
        @SerializedName("minPrice")
        val minPrice: Int?,
        @SerializedName("maxPrice")
        val maxPrice: Int?
    )

    data class MonthlyInfo(
        @SerializedName("minDeposit")
        val minDeposit: Int?,
        @SerializedName("maxDeposit")
        val maxDeposit: Int?,
        @SerializedName("minMonthPrice")
        val minMonthPrice: Int?,
        @SerializedName("maxMonthPrice")
        val maxMonthPrice: Int?
    )

    data class ResponseUseritem(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ResponseDongitem(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: DongitemData,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class DongitemData(
        @SerializedName("dong")
        val dong: String,
        @SerializedName("userItemResponses")
        val userItemResponses: List<UserItemResponse>
    )

    data class UserItemResponse(
        @SerializedName("userItemId")
        val userItemId: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("userNickname")
        val userNickname: String,
        @SerializedName("userItemAddressResponse")
        val userItemAddressResponse: UserItemAddressResponse,
        @SerializedName("userItemOptionsResponse")
        val userItemOptionsResponse: UserItemOptionsResponse
    )

    data class UserItemAddressResponse(
        @SerializedName("address")
        val address: String,
        @SerializedName("dong")
        val dong: String
    )

    data class UserItemOptionsResponse(
        @SerializedName("userOptionId")
        val userOptionId: Int,
        @SerializedName("userRoomTypes")
        val userRoomTypes: List<UserRoomType>,
        @SerializedName("userDealTypes")
        val userDealTypes: List<UserDealType>,
        @SerializedName("userRoomSizes")
        val userRoomSizes: List<UserRoomSize>,
        @SerializedName("userFloors")
        val userFloors: List<UserFloor>,
        @SerializedName("userManagementOptions")
        val userManagementOptions: List<UserManagementOption>,
        @SerializedName("userInternalFacilities")
        val userInternalFacilities: List<UserInternalFacility>,
        @SerializedName("approveDate")
        val approveDate: String,
        @SerializedName("userExtraFilters")
        val userExtraFilters: List<UserExtraFilter>
    )

    data class UserRoomType(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userRoomTypeId")
        val userRoomTypeId: Int,
        @SerializedName("roomType")
        val roomType: String
    )

    data class UserDealType(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("dealId")
        val dealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("minPrice")
        val minPrice: Int?,
        @SerializedName("maxPrice")
        val maxPrice: Int?,
        @SerializedName("minDeposit")
        val minDeposit: Int?,
        @SerializedName("maxDeposit")
        val maxDeposit: Int?,
        @SerializedName("minMonthPrice")
        val minMonthPrice: Int?,
        @SerializedName("maxMonthPrice")
        val maxMonthPrice: Int?
    )

    data class UserRoomSize(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userRoomSize")
        val userRoomSize: Int,
        @SerializedName("roomSize")
        val roomSize: String
    )

    data class UserFloor(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userFloorId")
        val userFloorId: Int,
        @SerializedName("floor")
        val floor: String
    )

    data class UserManagementOption(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userManagementOptionId")
        val userManagementOptionId: Int,
        @SerializedName("managementOption")
        val managementOption: String
    )

    data class UserInternalFacility(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userInternalFacilityId")
        val userInternalFacilityId: Int,
        @SerializedName("internalFacility")
        val internalFacility: String
    )

    data class UserExtraFilter(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("userExtraFilterId")
        val userExtraFilterId: Int,
        @SerializedName("extraFilter")
        val extraFilter: String
    )

    data class ResponseBrokeritem(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: List<BrokerItem>,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )
    data class BrokerItem(
        @SerializedName("brokerItemId")
        val brokerItemId: Int,
        @SerializedName("businessName")
        val businessName: String,
        @SerializedName("itemStatus")
        val itemStatus: String,
        @SerializedName("addressResponse")
        val addressResponse: AddressResponse,
        @SerializedName("detailResponse")
        val detailResponse: DetailResponse,
        @SerializedName("optionResponse")
        val optionResponse: OptionResponse
    )

    data class AddressResponse(
        @SerializedName("addressName")
        val addressName: String,
        @SerializedName("roadName")
        val roadName: String,
        @SerializedName("postNumber")
        val postNumber: String,
        @SerializedName("dong")
        val dong: String,
        @SerializedName("roadDong")
        val roadDong: String,
        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double
    )

    data class DetailResponse(
        @SerializedName("itemImages")
        val itemImages: List<ItemImage>,
        @SerializedName("itemContent")
        val itemContent: ItemContent
    )

    data class ItemImage(
        @SerializedName("itemImageId")
        val itemImageId: Int,
        @SerializedName("imageUrl")
        val imageUrl: String
    )

    data class ItemContent(
        @SerializedName("itemContentId")
        val itemContentId: Int,
        @SerializedName("shortIntroduction")
        val shortIntroduction: String,
        @SerializedName("specificIntroduction")
        val specificIntroduction: String
    )

    data class OptionResponse(
        @SerializedName("brokerOptionId")
        val brokerOptionId: Int,
        @SerializedName("dealTypes")
        val dealTypes: List<BrokerDealType>,
        @SerializedName("roomType")
        val roomType: String,
        @SerializedName("roomSize")
        val roomSize: String,
        @SerializedName("floors")
        val floors: List<BrokerFloor>,
        @SerializedName("managementOptions")
        val managementOptions: List<BrokerManagementOption>,
        @SerializedName("internalFacilities")
        val internalFacilities: List<BrokerInternalFacility>,
        @SerializedName("extraFilters")
        val extraFilters: List<BrokerExtraFilter>,
        @SerializedName("approvedDate")
        val approvedDate: String
    )

    data class BrokerDealType(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("brokerDealId")
        val brokerDealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("price")
        val price: String?,
        @SerializedName("deposit")
        val deposit: String?,
        @SerializedName("monthPrice")
        val monthPrice: String?
    )

    data class BrokerFloor(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("brokerFloorId")
        val brokerFloorId: Int,
        @SerializedName("floor")
        val floor: String?,
        @SerializedName("customFloor")
        val customFloor: String?
    )

    data class BrokerManagementOption(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("brokerManagementOptionId")
        val brokerManagementOptionId: Int,
        @SerializedName("managementOption")
        val managementOption: String,
        @SerializedName("managementPrice")
        val managementPrice: String
    )

    data class BrokerInternalFacility(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("brokerInternalFacilityId")
        val brokerInternalFacilityId: Int,
        @SerializedName("internalFacility")
        val internalFacility: String
    )

    data class BrokerExtraFilter(
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("updateAt")
        val updateAt: String,
        @SerializedName("brokerExtraFilterId")
        val brokerExtraFilterId: Int,
        @SerializedName("extraFilter")
        val extraFilter: String
    )

    data class RequestMatchbroker(
        @SerializedName("brokerItemId")
        val brokerItemId: List<Int>
    )

    data class ResponseMatchbroker(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: MatchBrokerData,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class MatchBrokerData(
        @SerializedName("matchDetails")
        val matchDetails: List<MatchDetail>
    )

    data class MatchDetail(
        @SerializedName("matchId")
        val matchId: Int,
        @SerializedName("userItemId")
        val userItemId: Int,
        @SerializedName("brokerItemId")
        val brokerItemId: Int
    )

    //매칭전체조회
//    data class ResponseDongitem(
//        @SerializedName("code")
//        val code: String,
//        @SerializedName("message")
//        val message: String,
//        @SerializedName("data")
//        val data: DongitemData,
//        @SerializedName("isSuccess")
//        val isSuccess: Boolean
//    )
//
//    data class DongitemData(
//        @SerializedName("matchListDetails")
//        val matchListDetails: List<MatchDetail>,
//        @SerializedName("isSuccess")
//        val isSuccess: Boolean
//    )
//
//    data class MatchDetail(
//        @SerializedName("matchId")
//        val matchId: Int,
//        @SerializedName("matchStatus")
//        val matchStatus: String,
//        @SerializedName("userItemResponse")
//        val userItemResponse: UserItemResponse,
//        @SerializedName("brokerItemResponse")
//        val brokerItemResponse: BrokerItemResponse
//    )
//
//    data class UserItemResponse(
//        @SerializedName("userItemId")
//        val userItemId: Int,
//        @SerializedName("userId")
//        val userId: Int,
//        @SerializedName("userNickname")
//        val userNickname: String,
//        @SerializedName("userItemAddressResponse")
//        val userItemAddressResponse: UserItemAddressResponse,
//        @SerializedName("userItemOptionsResponse")
//        val userItemOptionsResponse: UserItemOptionsResponse
//    )
//
//    data class UserItemAddressResponse(
//        @SerializedName("address")
//        val address: String,
//        @SerializedName("dong")
//        val dong: String
//    )
//
//    data class UserItemOptionsResponse(
//        @SerializedName("userOptionId")
//        val userOptionId: Int,
//        @SerializedName("userRoomTypes")
//        val userRoomTypes: List<UserRoomType>,
//        @SerializedName("userDealTypes")
//        val userDealTypes: List<UserDealType>,
//        @SerializedName("userRoomSizes")
//        val userRoomSizes: List<UserRoomSize>,
//        @SerializedName("userFloors")
//        val userFloors: List<UserFloor>,
//        @SerializedName("userManagementOptions")
//        val userManagementOptions: List<UserManagementOption>,
//        @SerializedName("userInternalFacilities")
//        val userInternalFacilities: List<UserInternalFacility>,
//        @SerializedName("approveDate")
//        val approveDate: String,
//        @SerializedName("userExtraFilters")
//        val userExtraFilters: List<UserExtraFilter>
//    )
//    data class BrokerItemResponse(
//        @SerializedName("brokerItemId")
//        val brokerItemId: Int,
//        @SerializedName("businessName")
//        val businessName: String,
//        @SerializedName("itemStatus")
//        val itemStatus: String,
//        @SerializedName("addressResponse")
//        val addressResponse: AddressResponse,
//        @SerializedName("detailResponse")
//        val detailResponse: DetailResponse,
//        @SerializedName("optionResponse")
//        val optionResponse: OptionResponse
//    )
    // 이사 일정
    data class Requestschedule(
        @SerializedName("period")
        val email: String,
        @SerializedName("moveDate")
        val password: String
    )

    data class Responseschedule(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ScheduleResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    //지도 검색
    // 검색 결과를 담는 클래스
    data class ResultSearchKeyword(
        var meta: PlaceMeta, // 장소 메타데이터
        var documents: List<Place> // 검색 결과
    )

    data class PlaceMeta(
        var total_count: Int, // 검색어에 검색된 문서 수
        var pageable_count: Int, // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
        var is_end: Boolean, // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
        var same_name: RegionInfo // 질의어의 지역 및 키워드 분석 정보
    )

    data class RegionInfo(
        var region: List<String>, // 질의어에서 인식된 지역의 리스트, ex) '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트
        var keyword: String, // 질의어에서 지역 정보를 제외한 키워드, ex) '중앙로 맛집' 에서 '맛집'
        var selected_region: String // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
    )

    data class Place(
        var id: String, // 장소 ID
        var place_name: String, // 장소명, 업체명
        var category_name: String, // 카테고리 이름
        var category_group_code: String, // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
        var category_group_name: String, // 중요 카테고리만 그룹핑한 카테고리 그룹명
        var phone: String, // 전화번호
        var address_name: String, // 전체 지번 주소
        var road_address_name: String, // 전체 도로명 주소
        var x: String, // X 좌표값 혹은 longitude
        var y: String, // Y 좌표값 혹은 latitude
        var place_url: String, // 장소 상세페이지 URL
        var distanc: String // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
    )

  
    data class ScheduleResult(
        @SerializedName("scheduleId")
        val scheduleId: String,
        @SerializedName("userId")
        val userId: String,
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: String
    )

}