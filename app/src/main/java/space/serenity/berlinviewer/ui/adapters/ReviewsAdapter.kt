package space.serenity.berlinviewer.ui.adapters

import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import space.serenity.berlinviewer.ui.binders.MyViewHolder


/**
 * Created by karmamaker on 31/05/2017.
 */
class ReviewsAdapter : RecyclerView.Adapter<MyViewHolder>() {

//    internal val VIEW_TYP_NEWS_TEASER = 0
//    internal val VIEW_TYP_FOOD_TIP = 1

//    internal var items: List<*> = new List<items>

    init {
//        newsTeaserDelegate = NewsTeaserAdapterDelegate(VIEW_TYP_NEWS_TEASER)
//        foodTipDelegate = PetFoodTipAdapterDelegate(VIEW_TYP_FOOD_TIP)
    }

    override fun getItemViewType(position: Int): Int {
        return 1;

//        if (newsTeaserDelegate.isForViewType(items, position)) {
//            return newsTeaserDelegate.getViewType()
//        } else if (foodTipDelegate.isForViewType(items, position)) {
//            return foodTipDelegate.getViewType()
//        }
//
//        throw IllegalArgumentException("No delegate found")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(parent)

//        if (newsTeaserDelegate.getViewType() === viewType) {
//            return newsTeaserDelegate.onCreateViewHolder(parent)
//        } else if (foodTipDelegate.getViewType() === viewType) {
//            return foodTipDelegate.onCreateViewHolder(parent)
//        }

//        throw IllegalArgumentException("No delegate found")
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val viewType = holder.getViewType()
//        if (ReviewBinder.getViewType() === viewType) {
//            newsTeaserDelegate.onBindViewHolder(items, position, holder)
//        } else if (foodTipDelegate.getViewType === viewType) {
//            foodTipDelegate.onBindViewHolder(items, position, holder)
//        }
    }

    override fun getItemCount(): Int {
        return 12
    }
}