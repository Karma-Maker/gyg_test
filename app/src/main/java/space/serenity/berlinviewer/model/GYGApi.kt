package space.serenity.berlinviewer.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by karmamaker on 31/05/2017.
 */

interface  GYGApi {
    @GET("/reviews.json")
    fun getReviews(@Query("page") page: Int,
                   @Query("count") count: Int) : Call<GYGReviewListResponse>


}