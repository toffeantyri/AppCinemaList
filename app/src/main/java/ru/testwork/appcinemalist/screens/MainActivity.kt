package ru.testwork.appcinemalist.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import butterknife.ButterKnife
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.testwork.appcinemalist.R
import ru.testwork.appcinemalist.appComponent
import ru.testwork.appcinemalist.log
import ru.testwork.appcinemalist.simpleScan
import ru.testwork.appcinemalist.view.DefaultLoadStateAdapter
import ru.testwork.appcinemalist.view.FilmsListAdapter
import ru.testwork.appcinemalist.viewmodels.MainActivityViewModel

@SuppressLint("NonConstantResourceId")
@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    lateinit var loadStateHolder : DefaultLoadStateAdapter.ViewHolder

    private val viewModel: MainActivityViewModel by viewModels()

    @BindView(R.id.film_list_recycler)
    lateinit var filmRecycler: RecyclerView

    @BindView(R.id.swipe_refresh)
    lateinit var swipeLayout: SwipeRefreshLayout

    @BindView(R.id.mainConstarin)
    lateinit var mainLayout: ConstraintLayout

    @BindView(R.id.loadStateView)
    lateinit var loadStateView : View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this@MainActivity)

        appComponent().inject(this)



        setupFilmsList()
        setupSwipeToRefresh()
        log("MainActivity onCreate")
    }


    private fun setupFilmsList() {
        val adapter = FilmsListAdapter()
        val tryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        filmRecycler.layoutManager = LinearLayoutManager(this)
        filmRecycler.adapter = adapterWithLoadState
        (filmRecycler.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false //todo
        loadStateHolder = DefaultLoadStateAdapter.ViewHolder(
            loadStateView,
            swipeLayout,
            tryAgainAction
        )

        observeFilms(adapter)
        observeLoadState(adapter)
        handleScrollingToTopWhenSearching(adapter)
        handleListVisibility(adapter)

    }


    private fun observeFilms(adapter: FilmsListAdapter) {
        lifecycleScope.launch {
            viewModel.filmFlow.collectLatest { pagingData ->

                log("mainActivity : collectLatest $pagingData")

                adapter.submitData(pagingData)
            }
        }
    }


    private fun observeLoadState(adapter: FilmsListAdapter) {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(500).collectLatest { state ->
                // main indicator in the center of the screen
                log("mainActivity : new loadState : $state")
                loadStateHolder.bind(state.refresh)

            }
        }
    }


    private fun handleScrollingToTopWhenSearching(adapter: FilmsListAdapter) = lifecycleScope.launch {
        // list should be scrolled to the 1st item (index = 0) if data has been reloaded:
        // (prev state = Loading, current state = NotLoading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    filmRecycler.scrollToPosition(0)
                }
            }
    }

    private fun handleListVisibility(adapter: FilmsListAdapter) = lifecycleScope.launch {
        // list should be hidden if an error is displayed OR if items are being loaded after the error:
        // (current state = Error) OR (prev state = Error)
        //   OR
        // (before prev state = Error, prev state = NotLoading, current state = Loading)
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                filmRecycler.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(adapter: FilmsListAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map {
            it.refresh }
    }


    private fun setupSwipeToRefresh() {
        swipeLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }
}
