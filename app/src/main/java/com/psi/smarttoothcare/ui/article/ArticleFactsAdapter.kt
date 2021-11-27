package com.psi.smarttoothcare.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.psi.smarttoothcare.databinding.RvItemFactBinding
import com.psi.smarttoothcare.model.Article

class ArticleFactsAdapter : RecyclerView.Adapter<ArticleFactsAdapter.ViewHolder>() {
    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(old: Article, new: Article) = old.url == new.url

        override fun areContentsTheSame(old: Article, new: Article) = old == new
    })
    private var onItemClickListener: ((Article) -> Unit)? = null

    inner class ViewHolder(val binding: RvItemFactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.tvEstimation.text = article.estimation
            Glide.with(itemView).load(article.image).into(binding.sivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvItemFactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

        holder.binding.root.setOnClickListener {
            onItemClickListener?.let { it(article) }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}