package com.example.mimiuchi.model.api.Response

//GETするJSONに合わせたクラス

//店舗一覧
data class ShopAll(
    val data: List<ShopData>
)

data class ShopData(
    val shopID: Int?,
    val shopName:String?,
    val pictureID: String?,
    val category: List<String>,
    var visitCount: Int?,
    var LastVisitedHours: Int?,
    val beaconID:String?,
    val response: String?
)

//メニュー一覧
data class MenuAll(
    val `data`: List<MenuData>
)

data class MenuData(
    val couponID: Int,
    val limit: String,
    val menuImgURL: String,
    val menuName: String,
    val releaseCount: Int,
    val response: String,
    val shopID: Int,
    val value: Int
)



//店舗詳細
data class ShopDetails(
    val data:ShopDetail
)

data class ShopDetail(
    val shopID: Int,
    val shopName: String?,
    val pictureID: String?,
    val category: List<String>,
    val visitCount: Int,
    val LastVisitedHours: Int,
    val streetAddress: String?,
    val phoneNumber: String?,
    val openHours: String?
)

//クーポン利用
data class CouponDelete(
    val response: String//適当
)

//クーポン一覧
data class CouponAll(
    val data: List<CouponData>
)

data class CouponData(
    val couponID: Int,
    val couponImgURL: String,
    val couponName: String,
    val limit: String,
    val permission: String,
    val response: String,
    val shopID: Int,
    val userID: Int,
    val value: Int
)

data class Login(
    val res: String,
    val userID: Int
)

data class New(
    val response: String,
    val userID: Int
)

data class Next(
    val count:Int,
    val next: Int
)