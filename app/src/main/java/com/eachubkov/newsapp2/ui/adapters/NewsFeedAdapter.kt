package com.eachubkov.newsapp2.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eachubkov.newsapp2.databinding.ItemNewsBigCardBinding
import com.eachubkov.newsapp2.databinding.ItemNewsSmallCardBinding
import com.eachubkov.newsapp2.ui.models.ArticleUI
import com.eachubkov.newsapp2.utils.Constants.Companion.BIG_CARD_NEWS
import com.eachubkov.newsapp2.utils.Constants.Companion.SMALL_CARD_NEWS

class NewsFeedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var newsList = emptyList<ArticleUI>()

    lateinit var onItemClickListener: ((ArticleUI) -> Unit)

    class BigCardNewsViewHolder(private val binding: ItemNewsBigCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentNews: ArticleUI) {
            binding.currentNews = currentNews
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): BigCardNewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsBigCardBinding.inflate(layoutInflater, parent, false)
                return BigCardNewsViewHolder(binding)
            }
        }
    }

    class SmallCardNewsViewHolder(private val binding: ItemNewsSmallCardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(currentNews: ArticleUI) {
            binding.currentNews = currentNews
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): SmallCardNewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemNewsSmallCardBinding.inflate(layoutInflater, parent, false)
                return SmallCardNewsViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when(viewType) {
        BIG_CARD_NEWS -> BigCardNewsViewHolder.from(parent)
        SMALL_CARD_NEWS -> SmallCardNewsViewHolder.from(parent)
        else -> BigCardNewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when(holder.itemViewType) {
        BIG_CARD_NEWS -> {
            (holder as BigCardNewsViewHolder).run {
                bind(currentNews = newsList[position])
                itemView.setOnClickListener {
                    onItemClickListener.invoke(newsList[position])
                }
            }
        }
        SMALL_CARD_NEWS -> {
            (holder as SmallCardNewsViewHolder).run {
                bind(currentNews = newsList[position])
                itemView.setOnClickListener {
                    onItemClickListener.invoke(newsList[position])
                }
            }
        }
        else -> (holder as BigCardNewsViewHolder).run {
            bind(currentNews = newsList[position])
            itemView.setOnClickListener {
                onItemClickListener.invoke(newsList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int = when(position % 10) {
        in 0..2 -> BIG_CARD_NEWS
        in 5..6 -> BIG_CARD_NEWS
        9 -> BIG_CARD_NEWS
        else -> SMALL_CARD_NEWS
    }

    override fun getItemCount(): Int = newsList.size

    fun setData(newNewsList: List<ArticleUI>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}