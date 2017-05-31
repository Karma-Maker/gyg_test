package space.serenity.berlinviewer.model

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by karmamaker on 31/05/2017.
 */

class RestAPI {
    private val gygApi: GYGApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

        gygApi = retrofit.create(GYGApi::class.java)
    }

    fun getReviews(page: Int, count: Int): Call<GYGReviewListResponse> {
        return gygApi.getReviews(page = page, count = count) // Just in case
    }
}