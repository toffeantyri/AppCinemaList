package ru.testwork.appcinemalist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import com.bumptech.glide.Glide
import ru.testwork.appcinemalist.R
import ru.testwork.appcinemalist.busines.model.FilmModelItem

class FilmsListAdapter : PagingDataAdapter<FilmModelItem, FilmsListAdapter.FilmHolder>(FilmsDiffUtilCallback()) {

    inner class FilmHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.tv_title)
        lateinit var title: TextView

        @BindView(R.id.tv_title2)
        lateinit var title2: TextView

        @BindView(R.id.iv_image_film)
        lateinit var image: ImageView

        fun bind(filmByPos: FilmModelItem) {
            title.text = filmByPos.title
            title2.text = filmByPos.title2
            loadFilmPhotoByUrl(image, filmByPos.imageLink)
        }

    }

    override fun onBindViewHolder(p0: FilmHolder, p1: Int) {
        val film = getItem(position = p1) ?: return
        p0.bind(film)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FilmHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.film_rv_item, p0, false)
        return FilmHolder(view)
    }


    fun loadFilmPhotoByUrl(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }


}


class FilmsDiffUtilCallback : DiffUtil.ItemCallback<FilmModelItem>() {

    //todo check Me
    override fun areItemsTheSame(p0: FilmModelItem, p1: FilmModelItem): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: FilmModelItem, p1: FilmModelItem): Boolean {
        return p0.title == p1.title
    }


}