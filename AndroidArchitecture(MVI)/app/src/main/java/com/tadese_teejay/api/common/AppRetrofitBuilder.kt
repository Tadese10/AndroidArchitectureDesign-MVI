package com.tadese_teejay.api.common

import com.tadese_teejay.utils.Constants
import com.tadese_teejay.utils.LiveDataCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofitBuilder {
        val baseUrl = Constants.BaseUrl
        val retrofitBuilder : Retrofit.Builder by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
        }
}