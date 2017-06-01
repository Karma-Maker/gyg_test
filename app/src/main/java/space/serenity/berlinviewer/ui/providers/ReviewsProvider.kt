package space.serenity.berlinviewer.ui.providers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import space.serenity.berlinviewer.model.GYGReviewListResponse
import space.serenity.berlinviewer.model.RestAPI
import space.serenity.berlinviewer.model.Review
import java.util.*

/**
 * Created by karmamaker on 01/06/2017.
 */
class ReviewsProvider() {
    private val source = ArrayList<Any>()
    protected var dataPadding: Int = 0 // Number of items before first item in source
    protected var lastLoadedPageSize = PAGE_SIZE

    internal var api = RestAPI()
    var dataSetChangeListener: () -> Unit = {}
    private var lastRequestFailed : Boolean = false


    protected var currCall: Call<GYGReviewListResponse>? = null

    fun init() {
        clear()
        currCall = requestPage(0, PAGE_SIZE, callback)
        source.add(ITEM_LOADING_FULLSCREEN)
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
                beforeResponse()
                if(response.body() != null) {
                    addPage(response.body().data)
                    onFinish()
                } else {
                    onFailure(call, Throwable("Bad response from server"))
                }
                lastRequestFailed = false
            }

            override fun onFailure(call: Call<GYGReviewListResponse>, t: Throwable) {
                beforeResponse()
                if(source.isEmpty()){
                    source.add(ITEM_NO_CONNECTION_FULLSCREEN)
                } else {
                    source.add(ITEM_NO_CONNECTION_SMALL)
                }
                lastRequestFailed = true
                onFinish()

            }

            private fun onFinish() {
                currCall = null
                notifyDataSetChanged()
            }

            private fun beforeResponse(){
                source.remove(ITEM_NO_CONNECTION_FULLSCREEN) // Not reliable but let it be for the time beeng
                source.remove(ITEM_NO_CONNECTION_SMALL)
                source.remove(ITEM_LOADING)
                source.remove(ITEM_LOADING_FULLSCREEN)
            }
        }

    private fun notifyDataSetChanged() {
        dataSetChangeListener.invoke();
    }

    operator fun get(position: Int): Any {
        val currentPage = (position + dataPadding) / PAGE_SIZE

        if (!lastRequestFailed
                && source.any { it is Review }
                && currCall == null
                && shouldRequestNextPage(position)) {
            source.remove(ITEM_NO_CONNECTION_FULLSCREEN) // Not reliable but let it be for the time beeng
            source.remove(ITEM_NO_CONNECTION_SMALL)
            source.remove(ITEM_LOADING_FULLSCREEN)
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

    companion object {
        val PAGE_SIZE = 32 // This value should be received from server
        val ITEMS_BEFORE_NEXT_REQUEST = 24 // This value should be received from server
        val ITEM_LOADING = "LOADING"
        val ITEM_LOADING_FULLSCREEN = "LOADING_FULLSCREEN"
        val ITEM_NO_REVIEWS = "NO_REVIEWS"
        val ITEM_NO_CONNECTION_SMALL = "NO_CONNECTION_SMALL "
        val ITEM_NO_CONNECTION_FULLSCREEN = "NO_CONNECTION_FULLSCREEN"
    }
}