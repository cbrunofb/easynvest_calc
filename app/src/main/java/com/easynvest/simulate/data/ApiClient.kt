package data

import com.easynvest.simulate.data.SimulateApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object ApiClient {

    private const val API_BASE_URL = "https://api-simulator-calc.easynvest.com.br"

    private var apiInterface: ApiInterface? = null

    fun build(): ApiInterface?{
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        apiInterface = retrofit.create(
            ApiInterface::class.java)

        return apiInterface as ApiInterface
    }

    interface ApiInterface{
        @GET("/calculator/simulate")
        fun simulate(
            @Query("investedAmount") investedAmount: Float,
            @Query("index") index: String,
            @Query("rate") rate: Float,
            @Query("isTaxFree") isTaxFree: String,
            @Query("maturityDate") maturityDate: String
        ): Call<SimulateApiResponse>
    }
    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

}