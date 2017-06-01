package space.serenity.berlinviewer.ui.binders

import android.view.ViewGroup
import space.serenity.berlinviewer.R

/**
 * Created by karmamaker on 31/05/2017.
 */

class DummyBinder(layoutId: Int, parent: ViewGroup) : Binder(layoutId, parent) {
    override fun bind(data: Any) {}
}
