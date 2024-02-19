package com.example.zipfront.connection

import android.media.Image
import com.google.gson.annotations.SerializedName
import java.util.Date

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

    data class Responselogout(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
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

    // 매물 새로 등록하기(공인중개사)
    data class RequestNewItem(
        @SerializedName("detailsRequest")
        val detailsRequest: Introduction,
        @SerializedName("optionsRequest")
        val optionsRequest: RequestUseritem,
        // 이미지를 이렇게 하는지 잘 모르겠음
        @SerializedName("multipartFiles")
        val multipartFiles: Image
    )

    data class Introduction(
        @SerializedName("shortIntroduction")
        val shortIntroduction: String,
        @SerializedName("specificIntroduction")
        val specificIntroduction: String
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
        val approveDate: ApproveDate?,
        @SerializedName("extraFilter")
        val extraFilter: List<ExtraFilter>
    )

    enum class RoomType {
        ONE_ROOM,
        TWO_OR_THREEROOM,
        OFFICETELS,
        APARTMENT
    }

    enum class DealType {
        CHARTER,
        TRADING,
        MONTHLY
    }

    enum class RoomSize {
        UNDER_17,
        UNDER_66,
        UNDER_99,
        UNDER_132,
        UNDER_165,
        UNDER_198,
        OVER_198
    }

    enum class Floor {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        OVER_SEVEN,
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
        val minPrice: String?,
        @SerializedName("maxPrice")
        val maxPrice: String?
    )

    data class CharterInfo2(
        @SerializedName("price")
        val price: String?,
        @SerializedName("deposit")
        val deposit: String?,
        @SerializedName("monthPrice")
        val monthPrice: String?
    )

    data class TradingInfo(
        @SerializedName("minPrice")
        val minPrice: String?,
        @SerializedName("maxPrice")
        val maxPrice: String?
    )

    data class TradingInfo2(
        @SerializedName("price")
        val price: String?,
        @SerializedName("deposit")
        val deposit: String?,
        @SerializedName("monthPrice")
        val monthPrice: String?
    )

    data class MonthlyInfo(
        @SerializedName("minMonthPrice")
        val minMonthPrice: String?,
        @SerializedName("maxMonthPrice")
        val maxMonthPrice: String?
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
        @SerializedName("dong")
        val dong: String,
        @SerializedName("userItemOptionsResponse")
        val userItemOptionsResponse: UserItemOptionsResponse
    )

//    data class UserItemAddressResponse(
//        @SerializedName("address")
//        val address: String,
//        @SerializedName("dong")
//        val dong: String
//    )

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
        @SerializedName("dealId")
        val dealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("minPrice")
        val minPrice: String?,
        @SerializedName("maxPrice")
        val maxPrice: String?,
        @SerializedName("minMonthPrice")
        val minMonthPrice: String?,
        @SerializedName("maxMonthPrice")
        val maxMonthPrice: String?
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
        @SerializedName("createdAt")
        val createdAt: String,
        @SerializedName("businessName")
        val businessName: String,
        @SerializedName("phoneNumber")
        val phoneNumber: String,
        @SerializedName("messageNumber")
        val messageNumber: String,
        @SerializedName("brokerImage")
        val brokerImage: Int,
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
        @SerializedName("brokerDealId")
        val brokerDealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("charterPrice")
        val charterPrice: String?,
        @SerializedName("tradingPrice")
        val tradingPrice: String?,
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

    data class RequestMatchbroker2(
        @SerializedName("matchingIds")
        val matchingIds: List<Int>,
        @SerializedName("matchStatus")
        val matchStatus: String
    )


    data class ResponseMatchbroker2(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class MatchBrokerData2(
        @SerializedName("matchId")
        val matchId: Int,
        @SerializedName("matchStatus")
        val matchStatus: String
    )

    //매칭전체조회
    data class ResponseMatchbrokeritem(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: DataMatchborker,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class DataMatchborker(
        @SerializedName("matchListDetails")
        val matchListDetails: List<MatchDetail2>
    )

    data class MatchDetail2(
        @SerializedName("matchId")
        val matchId: Int,
        @SerializedName("userImage")
        val userImage: String,
        @SerializedName("matchStatus")
        val matchStatus: String,
        @SerializedName("userItemResponse")
        val userItemResponse: UserItemResponse,
        @SerializedName("brokerItemResponse")
        val brokerItemResponse: BrokerItemResponse
    )

    data class BrokerItemResponse(
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

    // 일정 등록
    data class Requestschedule(
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: String
    )

    data class Responseschedule(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ScheduleResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ScheduleResult(
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: String
    )

    // 일정 수정
    data class Requestschedulemodify(
        @SerializedName("period")
        val email: String,
        @SerializedName("moveDate")
        val password: Date
    )

    data class Responseschedulemodify(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: SchedulemodifyResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class SchedulemodifyResult(
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: Date
    )

    // 일정 삭제
    data class Responsescheduledelete(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )


    // 일정 조회
    data class Responseschedulelookup(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: SchedulelookupResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class SchedulelookupResult(
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("period")
        val period: String,
        @SerializedName("moveDate")
        val moveDate: Date
    )

    // 상세 일정 조회
    data class ResponseEventScheduleLookup(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: List<EventSchedulelookupResult>,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class EventSchedulelookupResult(
        @SerializedName("eventId")
        val eventId: Int,
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("eventTitle")
        val eventTitle: String,
        @SerializedName("eventDate")
        val eventDate: String
    )

    // 상세 일정 수정
    // string은 어떻게 넣는지 모르겠음
    data class RequestEventschedulemodify(
        @SerializedName("eventId")
        val eventId: Int
    )

    data class ResponseEventschedulemodify(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ScheduleEventmodifyResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ScheduleEventmodifyResult(
        @SerializedName("eventId")
        val scheduleId: Int
    )

    // 상세 일정 삭제
    data class RequestEventscheduledelete(
        @SerializedName("eventId")
        val eventId: Int,
        @SerializedName("eventTitle")
        val eventTitle: String,
        @SerializedName("eventDate")
        val eventDate: Date
    )

    data class ResponseEventscheduledelete(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: EventScheduledeleteResult,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class EventScheduledeleteResult(
        @SerializedName("eventId")
        val eventId: Int,
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("eventTitle")
        val eventTitle: String,
        @SerializedName("eventDate")
        val eventDate: Date
    )

    // 매물 새로 등록하기 전 주소 입력
    data class ResponseBeforeAddress(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: List<BeforeAddress>,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class BeforeAddress(
        @SerializedName("address")
        val address: String,
        @SerializedName("road_address_name")
        val road_address_name: String,
        @SerializedName("postNumber")
        val postNumber: String
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


    //매물 상세정보 조회
    data class ResponseDetail(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: BrokerItem,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    //매물 삭제
    data class ResponseDelete(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: String,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    //유저 매칭 조회
    data class ResponseUserMatchitem(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: List<UserMatchitemData>,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class UserMatchitemData(
        @SerializedName("dong_requestName")
        val dongRequestName: String,
        @SerializedName("matchingCount")
        val matchingCount: Int,
        @SerializedName("matchedBrokerItemResponses")
        val matchedBrokerItemResponses: List<MatchedBrokerItemResponse>,
        @SerializedName("userRequestInfo")
        val userRequestInfo: UserRequestInfo
    )

    data class MatchedBrokerItemResponse(
        @SerializedName("matchingId")
        val matchingId: Int,
        @SerializedName("brokerItemId")
        val brokerItemId: Int,
        @SerializedName("businessName")
        val businessName: String,
        @SerializedName("itemStatus")
        val itemStatus: String,
        @SerializedName("dongName")
        val dongName: String,
        @SerializedName("detailResponse")
        val detailResponse: DetailResponse3,
        @SerializedName("optionResponse")
        val optionResponse: OptionResponse2
    )

    data class DetailResponse3(
        @SerializedName("itemImages")
        val itemImages: List<ItemImage>,
        @SerializedName("itemContent")
        val itemContent: ItemContent
    )

    data class OptionResponse2(
        @SerializedName("brokerOptionId")
        val brokerOptionId: Int,
        @SerializedName("dealTypes")
        val dealTypes: List<DealType2>,
        @SerializedName("roomType")
        val roomType: String,
        @SerializedName("roomSize")
        val roomSize: String,
        @SerializedName("floors")
        val floors: List<Floor2>,
        @SerializedName("managementOptions")
        val managementOptions: List<ManagementOption2>,
        @SerializedName("internalFacilities")
        val internalFacilities: List<InternalFacility2>,
        @SerializedName("extraFilters")
        val extraFilters: List<ExtraFilter2>,
        @SerializedName("approvedDate")
        val approvedDate: String
    )

    data class DealType2(
        @SerializedName("brokerDealId")
        val brokerDealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("charterPrice")
        val charterPrice: String?,
        @SerializedName("tradingPrice")
        val tradingPrice: String?,
        @SerializedName("monthPrice")
        val monthPrice: String?
    )

    data class Floor2(
        @SerializedName("brokerFloorId")
        val brokerFloorId: Int,
        @SerializedName("floor")
        val floor: String?,
        @SerializedName("customFloor")
        val customFloor: String?
    )

    data class ManagementOption2(
        @SerializedName("brokerManagementOptionId")
        val brokerManagementOptionId: Int,
        @SerializedName("managementOption")
        val managementOption: String,
        @SerializedName("managementPrice")
        val managementPrice: String
    )

    data class InternalFacility2(
        @SerializedName("brokerInternalFacilityId")
        val brokerInternalFacilityId: Int,
        @SerializedName("internalFacility")
        val internalFacility: String
    )

    data class ExtraFilter2(
        @SerializedName("brokerExtraFilterId")
        val brokerExtraFilterId: Int,
        @SerializedName("extraFilter")
        val extraFilter: String
    )

    data class UserRequestInfo(
        @SerializedName("userItemId")
        val userItemId: Int,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("userNickname")
        val userNickname: String,
        @SerializedName("dong")
        val dong: String,
        @SerializedName("userItemOptionsResponse")
        val userItemOptionsResponse: UserItemOptionsResponse2
    )

    data class UserItemOptionsResponse2(
        @SerializedName("userOptionId")
        val userOptionId: Int,
        @SerializedName("userRoomTypes")
        val userRoomTypes: List<UserRoomType2>,
        @SerializedName("userDealTypes")
        val userDealTypes: List<UserDealType2>,
        @SerializedName("userRoomSizes")
        val userRoomSizes: List<UserRoomSize2>,
        @SerializedName("userFloors")
        val userFloors: List<UserFloor2>,
        @SerializedName("userManagementOptions")
        val userManagementOptions: List<UserManagementOption2>,
        @SerializedName("userInternalFacilities")
        val userInternalFacilities: List<UserInternalFacility2>,
        @SerializedName("approveDate")
        val approveDate: String,
        @SerializedName("userExtraFilters")
        val userExtraFilters: List<UserExtraFilter2>
    )

    data class UserRoomType2(
        @SerializedName("userRoomTypeId")
        val userRoomTypeId: Int,
        @SerializedName("roomType")
        val roomType: String
    )

    data class UserDealType2(
        @SerializedName("dealId")
        val dealId: Int,
        @SerializedName("dealType")
        val dealType: String,
        @SerializedName("minPrice")
        val minPrice: String?,
        @SerializedName("maxPrice")
        val maxPrice: String?,
        @SerializedName("minMonthPrice")
        val minMonthPrice: String?,
        @SerializedName("maxMonthPrice")
        val maxMonthPrice: String?
    )

    data class UserRoomSize2(
        @SerializedName("userRoomSize")
        val userRoomSize: Int,
        @SerializedName("roomSize")
        val roomSize: String
    )

    data class UserFloor2(
        @SerializedName("userFloorId")
        val userFloorId: Int,
        @SerializedName("floor")
        val floor: String?
    )

    data class UserManagementOption2(
        @SerializedName("userManagementOptionId")
        val userManagementOptionId: Int,
        @SerializedName("managementOption")
        val managementOption: String
    )

    data class UserInternalFacility2(
        @SerializedName("userInternalFacilityId")
        val userInternalFacilityId: Int,
        @SerializedName("internalFacility")
        val internalFacility: String
    )

    data class UserExtraFilter2(
        @SerializedName("userExtraFilterId")
        val userExtraFilterId: Int,
        @SerializedName("extraFilter")
        val extraFilter: String
    )

    data class detailRequest(
        @SerializedName("itemImages")
        val itemImages: List<ItemImage>,
        @SerializedName("itemContent")
        val itemContent: ItemContent
    )

    data class optionsRequest(
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


    //유저 home
    data class ResponseHome(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: HomeMatchitemData,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    //매물 판매완료
    data class ResponseSoldOut(
        @SerializedName("code")
        val code: Int,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ItemStatus,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class HomeMatchitemData(
        @SerializedName("matchedItems")
        val matchedItems: MatchedItems,
        @SerializedName("movingSchedule")
        val movingSchedule: MovingSchedulehome? // 현재 null 값을 허용하도록 설정했으나 필요에 따라 타입 변경 가능
    )

    data class MatchedItems(
        @SerializedName("dong")
        val dong: String,
        @SerializedName("matchingCount")
        val matchingCount: Int,
        @SerializedName("matchedBrokerItemResponses")
        val matchedBrokerItemResponses: List<HomeItemResponse>
    )

    data class HomeItemResponse(
        @SerializedName("matchingId")
        val matchingId: Int,
        @SerializedName("brokerItemId")
        val brokerItemId: Int,
        @SerializedName("businessName")
        val businessName: String,
        @SerializedName("itemStatus")
        val itemStatus: String,
        @SerializedName("dongName")
        val dongName: String,
        @SerializedName("detailResponse")
        val detailResponse: DetailResponse3,
        @SerializedName("optionResponse")
        val optionResponse: OptionResponse2
    )

    data class MovingSchedulehome(
        @SerializedName("month")
        val month: Int,
        @SerializedName("event")
        val event: Eventhome
    )

    data class Eventhome(
        @SerializedName("eventId")
        val eventId: Int,
        @SerializedName("scheduleId")
        val scheduleId: Int,
        @SerializedName("eventTitle")
        val eventTitle: String,
        @SerializedName("eventDate")
        val eventDate: String
    )

    data class ItemStatus(
        @SerializedName("brokerItemId")
        val brokerItemId: Int,
        @SerializedName("itemStatus")
        val itemStatus: String
    )

    //매물 정보 동 필터링
    data class ResponseLocationFilter(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("data")
        val data: ItemList,
        @SerializedName("isSuccess")
        val isSuccess: Boolean
    )

    data class ItemList(
        @SerializedName("count")
        val count: Int,
        @SerializedName("brokerItemListList")
        val brokerItemListList: List<BrokerItemList>
    )

    data class BrokerItemList(
        @SerializedName("brokerItemId")
        val brokerItemId: Int,
        @SerializedName("itemStatus")
        val itemStatus: String,
        @SerializedName("dealTypes")
        val dealTypes: List<DealType2>,
        @SerializedName("roomSize")
        val roomSize: String,
        @SerializedName("floors")
        val floors: List<Floor2>,
        @SerializedName("managementOptions")
        val managementOptions: List<ManagementOption2>,
        @SerializedName("addressName")
        val addressName: String,
        @SerializedName("roadName")
        val roadName: String,
        @SerializedName("shortIntroduction")
        val shortIntroduction: String,
        @SerializedName("itemImage")
        val itemImage: List<ItemImage2>
    )

    data class ItemImage2(
        @SerializedName("itemImageId")
        val itemImageId: Int,
        @SerializedName("itemImage")
        val itemImage: String
    )

    data class RequestLocation(
        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double
    )


}