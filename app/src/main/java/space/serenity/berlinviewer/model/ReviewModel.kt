package space.serenity.berlinviewer.model

/**
 * Created by karmamaker on 31/05/2017.
 */

data class Review(
        val review_id: Long,
        val rating: Float,
        val title: String,
        val message: String,
        val date: String, // I am very suspicious about this field
        val author: String,
        val reviewerCountry: String
)