package space.serenity.berlinviewer.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karmamaker on 31/05/2017.
 */

interface  GYGApi {
    @GET("https://www.getyourguide.com/berlin-l17/tempelhof-2-hour-airport-history-tour-berlin-airlift-more-t23776/reviews.json")
    fun getReviews(@Query("page") page: Int,
                   @Query("count") count: Int) : Call<GYGReviewListResponse>
}