package com.example.mimiuchi.model.api

import com.example.mimiuchi.model.api.Response.Next
import retrofit2.Call
import retrofit2.http.*


interface ApiService {


    /**
     * 店舗一覧
     */
    @GET("api/shopAll")
    fun shopAll(@Query("userID") userId: Int): Call<String>

    /**
     * メニュー一覧
     */
    @GET("api/menuAll")
    fun menuAll(@Query("userID") userId: Int, @Query("shopID") shopId: Int): Call<String>


    /**
     * クーポン一覧
     */
    @GET("api/couponAll")
    fun couponAll(@Query("userID") userId: Int, @Query("shopID") shopId: Int): Call<String>
    //@Query("userId") userId: Int, @Query("shopId") shopId: Int


    /**
     * 店舗詳細
     */
    @GET("api/shopDetail")
    fun shopDetail( @Query("shopID") shopId: Int): Call<String>


    /**
     * クーポン削除
     */
    @GET("api/couponUse")
    fun couponDelete(@Query("userID") userId: Int, @Query("shopID") shopId: Int, @Query("coupon_ID") couponId:String):Call<String>

    /**
     * ログイン
     */
    @FormUrlEncoded
    @POST("user/login")
    fun login(@Field("userName") userName: String?, @Field("password") password:String?):Call<String>

    /**
     * 新規アカウント作成
     */
    @FormUrlEncoded
    @POST("user/new")
    fun createAcaunt(@Field("userName") userName: String?,@Field("password") password: String?, @Field("userSex") userSex: String?, @Field("userAge") userAge: Int):Call<String>


    /**
     * 来店回数増加など
     */
    @GET("api/shopComming")
    fun shopComming(@Query("userID") userId: Int, @Query("shopID") shopId: Int):Call<String>

    /**
     * DB初期化
     */
    @GET("init/db")
    fun initDb(@Query("response") response: String?):Call<String>

    @GET("api/nextMenu")
    fun nextMenu(@Query("userID")userId: Int,@Query("shopID") shopId: Int):Call<Next>
}