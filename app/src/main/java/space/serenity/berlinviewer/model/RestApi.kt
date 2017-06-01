package space.serenity.berlinviewer.model

import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


/**
 * Created by karmamaker on 31/05/2017.
 */

class RestAPI {
    private val gygApi: GYGApi

    init {
        var okHttpClient = OkHttpClient.Builder()
                .dns(Dns.SYSTEM)
                .addInterceptor(HeaderInterceptor())
                .protocols(Arrays.asList(Protocol.HTTP_1_1)).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.getyourguide.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()

        gygApi = retrofit.create(GYGApi::class.java)
    }

    fun getReviews(page: Int, count: Int): Call<GYGReviewListResponse> {
        return gygApi.getReviews(page = page, count = count, sortBy = "date_of_review", direction = "DESC")
    }
}

interface GYGApi {
    @GET("berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json")
    fun getReviews(@Query("page") page: Int,
                   @Query("count") count: Int,
                   @Query("sortBy") sortBy: String,
                   @Query("direction") direction: String): Call<GYGReviewListResponse>
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest: Request

        newRequest = request.newBuilder()
                .header("User-Agent", "not_ok_http") // I've got some questions
                .build()
        return chain.proceed(newRequest)
    }
}