package space.serenity.berlinviewer.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import space.serenity.berlinviewer.R
import space.serenity.berlinviewer.model.Review
import space.serenity.berlinviewer.ui.binders.*
import space.serenity.berlinviewer.ui.providers.ReviewsProvider
import space.serenity.berlinviewer.ui.providers.ReviewsProvider.Companion.ITEM_LOADING_FULLSCREEN
import space.serenity.berlinviewer.ui.providers.ReviewsProvider.Companion.ITEM_NO_CONNECTION_FULLSCREEN
import space.serenity.berlinviewer.ui.providers.ReviewsProvider.Companion.ITEM_NO_CONNECTION_SMALL
import space.serenity.berlinviewer.ui.providers.ReviewsProvider.Companion.ITEM_NO_REVIEWS


/**
 * Created by karmamaker on 31/05/2017.
 */
class ReviewsAdapter(val provider : ReviewsProvider) : RecyclerView.Adapter<Binder>() {
    override fun getItemViewType(position: Int): Int {
        val item = provider.get(position)
        when (item){
            is Review -> return 1
            ITEM_NO_REVIEWS -> return 2
            ITEM_NO_CONNECTION_SMALL -> return 3
            ITEM_NO_CONNECTION_FULLSCREEN -> return 4
            ITEM_LOADING_FULLSCREEN -> return 5
            else -> return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Binder {
        when (viewType){
            1 -> return ReviewBinder(parent)
            2 -> return DummyBinder(R.layout.item_no_reviews, parent)
            3 -> return DummyBinder(R.layout.item_no_connection_small, parent)
            4 -> return DummyBinder(R.layout.item_no_connection_fullscreen, parent)
            5 -> return DummyBinder(R.layout.item_loading_fullscreen, parent)

            else -> return DummyBinder(R.layout.item_loading_small, parent)
        }
    }


    override fun onBindViewHolder(holder: Binder, position: Int) {
        holder.bind(provider.get(position))
    }

    override fun getItemCount(): Int {
        return provider.count
    }
}

