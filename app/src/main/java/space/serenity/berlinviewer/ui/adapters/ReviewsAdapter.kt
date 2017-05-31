package space.serenity.berlinviewer.ui.adapters

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import space.serenity.berlinviewer.R
import space.serenity.berlinviewer.ui.binders.MyViewHolder
import space.serenity.berlinviewer.ui.providers.ReviewsProvider


/**
 * Created by karmamaker on 31/05/2017.
 */
class ReviewsAdapter : RecyclerView.Adapter<MyViewHolder>() {

    val provider = ReviewsProvider(this) // FIXME May leak !!!!! Event based ?

    fun deleteMeInit(){
        provider.init();
    } //FIXME do as I said

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val reviewLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return MyViewHolder(reviewLayout)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(provider.get(position))
    }

    override fun getItemCount(): Int {
        return provider.count
    }
}