package space.serenity.berlinviewer.ui


import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

import space.serenity.berlinviewer.R
import kotlinx.android.synthetic.main.dialog_submit_review.*


class SubmitReviewDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        val view = inflater.inflate(R.layout.dialog_submit_review, container, false)
        submitBtn.setOnClickListener {  }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        // creating the fullscreen dialog
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_submit_review)
        dialog.window!!.setBackgroundDrawable(null)
        dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        return dialog
    }

    companion object {

        fun show(activity: Activity) {
            val newFragment = SubmitReviewDialog()
            newFragment.show(activity.fragmentManager, "SubmitReviewDialog")
        }
    }
}