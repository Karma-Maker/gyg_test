package space.serenity.berlinviewer.ui.binders

import android.text.Html
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_review.view.*
import space.serenity.berlinviewer.R
import space.serenity.berlinviewer.model.Review

/**
 * Created by karmamaker on 31/05/2017.
 */

class ReviewBinder(parent: ViewGroup) : Binder(R.layout.item_review, parent) {
    override fun bind(data: Any) {
        if (data is Review) {
            itemView.title.text = Html.fromHtml(data.title) // Non depricated methods appears only in api 24
            itemView.rating.rating = data.rating
            itemView.message.text = Html.fromHtml(data.message)
            itemView.userData.text = "${data.author} • ${data.date}" // I really think it's ok to leave it as it is without extracting format to string resources
        }
    }
}
