package space.serenity.berlinviewer.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_launch.*
import space.serenity.berlinviewer.R
import space.serenity.berlinviewer.ui.adapters.ReviewsAdapter
import space.serenity.berlinviewer.ui.providers.ReviewsProvider


class LaunchActivity : AppCompatActivity() {
    val provider = ReviewsProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        //menu
        setSupportActionBar(toolbar)

        // list
        list.layoutManager = LinearLayoutManager(this)
        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        val adapter = ReviewsAdapter(provider)
        list.adapter = adapter

        provider.dataSetChangeListener = {
            refresh.isRefreshing = false
            adapter.notifyDataSetChanged()
        }
        refresh.setOnRefreshListener { provider.init() }

        // fab
        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> SubmitReviewDialog.show(this) }
    }


    override fun onStart() {
        super.onStart()
        provider.init();

    }

    override fun onStop() {
        super.onStop()
        provider.clear();
        refresh.isRefreshing = false
    }
}
