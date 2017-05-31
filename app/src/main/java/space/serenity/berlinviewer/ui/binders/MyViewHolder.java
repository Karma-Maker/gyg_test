package space.serenity.berlinviewer.ui.binders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by karmamaker on 31/05/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {
    public MyViewHolder(View itemView) {
        super(itemView);
    }

    public int getViewType(){
        return 1;
    }
}
