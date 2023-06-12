package org.d3if3117.assesment2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if3117.assesment2.tampil_info.Finance
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface MoneyApiService {
    private const  val BASE_URL = "https://raw.githubusercontent.com/virginyosse/picsMenuInfo/main"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

    interface MoneyApiService {
        @GET("MenuInfo.json")
        suspend fun getFinance(): List<Finance>
    }

    object FinanceApi {
        val service: MoneyApiService by lazy {
            retrofit.create(MoneyApiService::class.java)
        }
        fun getFinanceUrl(nama: String): String {
            return "$BASE_URL$nama"
        }
    }
    enum class ApiStatus { LOADING, SUCCESS, FAILED }
}