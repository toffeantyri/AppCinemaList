package ru.testwork.appcinemalist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import ru.testwork.appcinemalist.R


class DefaultLoadStateAdapter(private val tryAgainAction: () -> Unit) :
    LoadStateAdapter<DefaultLoadStateAdapter.ViewHolder>() {


    class ViewHolder(
        private val view: View,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val tryAgainAction: () -> Unit
    ) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.error_msg_item)
        lateinit var errorMessage: TextView

        @BindView(R.id.retry_btn)
        lateinit var retryButton: Button

        @BindView(R.id.progress_bar_item)
        lateinit var progressBarState: ProgressBar

        init {
            retryButton.setOnClickListener { tryAgainAction }
        }


        fun bind(loadState: LoadState) {
            errorMessage.isVisible = loadState is LoadState.Error
            retryButton.isVisible = loadState is LoadState.Error
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                progressBarState.isVisible = false
            } else {
                progressBarState.isVisible = loadState is LoadState.Loading
            }
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.network_state_item, parent, false)
        return ViewHolder(view, null, tryAgainAction = tryAgainAction)
    }

}