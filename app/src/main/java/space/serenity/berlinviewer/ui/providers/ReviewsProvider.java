package space.serenity.berlinviewer.ui.providers;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import space.serenity.berlinviewer.model.GYGReviewListResponse;
import space.serenity.berlinviewer.model.RestAPI;
import space.serenity.berlinviewer.model.Review;
import space.serenity.berlinviewer.ui.adapters.ReviewsAdapter;


/**
 * Created by karmamaker on 03.10.15.
 */
public class ReviewsProvider {
    private List<Review> source = new ArrayList<>();
    public static final int PAGE_SIZE = 32; // This value should be received from server
    public static final int ITEMS_BEFORE_NEXT_REQUEST = 24; // This value should be received from server
    protected int dataPadding; // Number of items before first item in source
    protected int lastLoadedPageSize = PAGE_SIZE;
    private ReviewsAdapter adapter;

    RestAPI api = new RestAPI(); // FIXME BS

    public ReviewsProvider(ReviewsAdapter adapter) {
        this.adapter = adapter;
    }

    protected Call<GYGReviewListResponse> currCall;

    public void init() {
        clear();
        currCall = requestPage(0, PAGE_SIZE, getCallback());
    }

    public void refresh() {
        init();
    }

    public void cancel(){
        if(currCall != null) {
            currCall.cancel();
            currCall = null;
        }
    }

    public void clear() {
        dataPadding = 0;
        lastLoadedPageSize = PAGE_SIZE;
    }

    public boolean isFullyLoaded(){
        return lastLoadedPageSize != PAGE_SIZE;
    }

    public Call<GYGReviewListResponse> requestPage(int pageNumber, int pageSize, Callback<GYGReviewListResponse> callback){
        Call<GYGReviewListResponse> call = api.getReviews(pageNumber, pageSize);
        call.enqueue(callback);
        return call;
    }

    protected Callback<GYGReviewListResponse> getCallback() {
        return new Callback<GYGReviewListResponse>() {
            @Override
            public void onResponse(Call<GYGReviewListResponse> call, Response<GYGReviewListResponse> response) {
                addPage(response.body().getData());
                notifyDataSetChanged();
                onFinish(call);
            }

            @Override
            public void onFailure(Call<GYGReviewListResponse> call, Throwable t) {
                onFinish(call);
            }

            private void onFinish(Call<GYGReviewListResponse> call) {
                currCall = null;
            }
        };
    }

    private void notifyDataSetChanged(){
        adapter.notifyDataSetChanged();
    }

    public Review get(int position) {
        int currentPage = (position + dataPadding) / PAGE_SIZE;

        if(shouldRequestNextPage(position)){
            currCall = requestPage(currentPage + 1, PAGE_SIZE, getCallback());
        }
        return source.get(position);
    }

    public int getCount() {
        return source.size();
    }

    private boolean shouldRequestNextPage(int position) {
        return !isFullyLoaded()
                && source.size() - ITEMS_BEFORE_NEXT_REQUEST < position
                && currCall == null;
    }

    protected void addPage(List loadedPage) {
        lastLoadedPageSize = loadedPage.size();
        int dataSizeWithDuplicates = loadedPage.size();
        loadedPage.removeAll(source);

        dataPadding += dataSizeWithDuplicates - loadedPage.size();
        source.addAll(source.size(), loadedPage);
    }
}

