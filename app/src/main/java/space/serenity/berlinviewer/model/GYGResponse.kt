package space.serenity.berlinviewer.model

/**
 * Created by karmamaker on 31/05/2017.
 */

data class GYGReviewListResponse (
        val status: Boolean,
        val total_reviews: Int,
        val data: List<Review>
)


data class RedditResponse (
        val kind: String,
        val data: Object
)