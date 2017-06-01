package space.serenity.berlinviewer.ui.providers

import android.app.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.serenity.berlinviewer.model.GYGReviewListResponse
import space.serenity.berlinviewer.model.RestAPI
import space.serenity.berlinviewer.model.Review
import space.serenity.berlinviewer.ui.adapters.ReviewsAdapter
import java.util.ArrayList

/**
 * Created by karmamaker on 01/06/2017.
 */
class ReviewsProvider() {
    private val source = ArrayList<Any>()
    protected var dataPadding: Int = 0 // Number of items before first item in source
    protected var lastLoadedPageSize = PAGE_SIZE

    internal var api = RestAPI() // FIXME BS
    var dataSetChangeListener: () -> Unit = {}


    protected var currCall: Call<GYGReviewListResponse>? = null

    fun init() {
        clear()
        currCall = requestPage(0, PAGE_SIZE, callback)
    }

    fun clear() {
        dataPadding = 0
        lastLoadedPageSize = PAGE_SIZE
        val savedCall = currCall
        if(savedCall != null){
            savedCall.cancel()
            currCall = null
        }
    }

    val isFullyLoaded: Boolean
        get() = lastLoadedPageSize != PAGE_SIZE

    fun requestPage(pageNumber: Int, pageSize: Int, callback: Callback<GYGReviewListResponse>): Call<GYGReviewListResponse> {
        val call = api.getReviews(pageNumber, pageSize)
        call.enqueue(callback)
        return call
    }

    protected val callback: Callback<GYGReviewListResponse>
        get() = object : Callback<GYGReviewListResponse> {
            override fun onResponse(call: Call<GYGReviewListResponse>, response: Response<GYGReviewListResponse>) {
                if(response.body() != null) {
                    addPage(response.body().data)
                    notifyDataSetChanged()
                    onFinish(call)
                } else {
                    onFailure(call, Throwable("Bad response from server"))
                }

            }

            override fun onFailure(call: Call<GYGReviewListResponse>, t: Throwable) {
                if(isEmpty()){
                    source.clear()
                    source.add(ITEM_NO_CONNECTION_FULLSCREEN)
                } else {
                    source.add(ITEM_NO_CONNECTION_SMALL)
                }
                onFinish(call)
            }

            private fun onFinish(call: Call<GYGReviewListResponse>) {
                currCall = null
                source.remove(ITEM_LOADING)
            }
        }

    private fun notifyDataSetChanged() {
        dataSetChangeListener.invoke();
    }

    operator fun get(position: Int): Any {
        val currentPage = 0 //(position + dataPadding) / PAGE_SIZE FIXME

        if (shouldRequestNextPage(position)) {
            source.add(ITEM_LOADING)
            currCall = requestPage(currentPage + 1, PAGE_SIZE, callback)
        }
        return source[position]
    }

    val count: Int
        get() = source.size

    private fun shouldRequestNextPage(position: Int): Boolean {
        return !isFullyLoaded
                && source.size - ITEMS_BEFORE_NEXT_REQUEST < position
                && currCall == null
    }

    fun addPage(loadedPage: MutableList<Review>) {
        lastLoadedPageSize = loadedPage.size

        val dataSizeWithDuplicates = loadedPage.size
        loadedPage.removeAll(source)

        dataPadding += dataSizeWithDuplicates - loadedPage.size
        source.addAll(source.size, loadedPage)

    }

    fun isEmpty() : Boolean{
        return !source.any({it is Review })
    }

    companion object {
        val PAGE_SIZE = 32 // This value should be received from server
        val ITEMS_BEFORE_NEXT_REQUEST = 24 // This value should be received from server
        val ITEM_LOADING = "LOADING"
        val ITEM_NO_REVIEWS = "NO_REVIEWS"
        val ITEM_NO_CONNECTION_SMALL = "NO_CONNECTION_SMALL "
        val ITEM_NO_CONNECTION_FULLSCREEN = "NO_CONNECTION_FULLSCREEN"
    }
}