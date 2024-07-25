package com.plbertheau.data.service

import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody


//class ApiResponseInterceptor : Interceptor {
//    companion object {
//        val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8")
//        val GSON: Gson = Gson()
//    }
//
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request: Request = chain.request()
//        val response = chain.proceed(request)
//        val body = response.body()
//        val apiResponse: ApiResponse = GSON.fromJson(body!!.string(), ApiResponse::class.java)
//        body!!.close()
//
//        // TODO any logic regarding ApiResponse#status or #code you need to do
//
//        val newResponse = response.newBuilder()
//            .body(ResponseBody.create(JSON, apiResponse.data.toString()))
//        return newResponse.build()
//    }
//}