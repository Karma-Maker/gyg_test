package space.serenity.berlinviewer.ui.binders

import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.item_review.view.*
import space.serenity.berlinviewer.model.Review

/**
 * Created by karmamaker on 31/05/2017.
 */

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(review: Review){
        itemView.message.text = Html.fromHtml(review.message)
    }
}
