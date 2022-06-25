package ru.testwork.appcinemalist.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import dagger.hilt.android.AndroidEntryPoint
import ru.testwork.appcinemalist.R
import ru.testwork.appcinemalist.viewmodels.MainActivityViewModel

@SuppressLint("NonConstantResourceId")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()


    @BindView(R.id.film_list_recycler)
    lateinit var filmRecycler: RecyclerView

    @BindView(R.id.swipe_refresh)
    lateinit var swipeLayout: SwipeRefreshLayout

    @BindView(R.id.progress)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.tv_error)
    lateinit var tvError: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}
