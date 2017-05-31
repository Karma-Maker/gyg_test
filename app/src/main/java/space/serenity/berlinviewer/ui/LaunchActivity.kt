package space.serenity.berlinviewer.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import space.serenity.berlinviewer.R
import kotlinx.android.synthetic.main.activity_launch.*
import space.serenity.berlinviewer.ui.adapters.ReviewsAdapter


class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        list.layoutManager = LinearLayoutManager(this)
        val adapter = ReviewsAdapter();
        list.adapter = adapter
        adapter.deleteMeInit()
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
