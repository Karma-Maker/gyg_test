package space.serenity.berlinviewer.model

import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
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
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(okHttpClient)
                .build()

        gygApi = retrofit.create(GYGApi::class.java)
    }

    fun getReviews(page: Int, count: Int): Call<GYGReviewListResponse> {
        return gygApi.getReviews(page = page, count = count) // FIXME hardcode !!!
    }

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val newRequest: Request

            newRequest = request.newBuilder()
                    .header("User-Agent", "not_ok_http")
                    .build()
            return chain.proceed(newRequest)
        }
    }
}