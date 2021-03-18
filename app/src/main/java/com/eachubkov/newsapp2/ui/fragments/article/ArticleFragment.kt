package com.eachubkov.newsapp2.ui.fragments.article

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eachubkov.newsapp2.R
import com.eachubkov.newsapp2.databinding.FragmentArticleBinding
import com.eachubkov.newsapp2.utils.addUnderline
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment(R.layout.fragment_article) {

    private val binding by viewBinding(FragmentArticleBinding::bind)

    private val args by navArgs<ArticleFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.article = args.currentArticle

        with(binding.articleLinkTextView) {
            addUnderline(text = text.toString())
            setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(args.currentArticle.url)))
            }
        }
    }
}