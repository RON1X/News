package com.eachubkov.newsapp2.ui.fragments.newsfeed

import android.content.res.Configuration
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.eachubkov.newsapp2.R
import com.eachubkov.newsapp2.databinding.FragmentNewsFeedBinding
import com.eachubkov.newsapp2.ui.adapters.NewsFeedAdapter
import com.eachubkov.newsapp2.ui.models.mapToUI
import com.eachubkov.newsapp2.utils.*
import com.eachubkov.newsapp2.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NewsFeedFragment : Fragment(R.layout.fragment_news_feed) {

    private val binding by viewBinding(FragmentNewsFeedBinding::bind)

    private val newsViewModel by viewModels<NewsViewModel>()

    private val newsFeedAdapter by lazy { NewsFeedAdapter() }

    private val networkListener by lazy { NetworkListener() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        setupThemeChanger()

        setupSearch()

        setupCategory()

        setupRecyclerView()

        lifecycleScope.launch {
            networkListener.checkNetworkAvailability(requireContext()).collect {
                newsViewModel.showNetworkStatus(status = it)
            }
        }

        if (newsViewModel.newsResponse.value == null) newsViewModel.getNews()

        newsViewModel.newsResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Error -> requireContext().toast(response.message)
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    response.data?.let { newsResponse ->
                        newsFeedAdapter.setData(newsResponse.articles.map { it.mapToUI() })
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        binding.newsFeedRecyclerView.apply {
            adapter = newsFeedAdapter
            layoutManager = LinearLayoutManager(requireContext())
            isMotionEventSplittingEnabled = false
        }
        newsFeedAdapter.onItemClickListener = { article ->
            val action = NewsFeedFragmentDirections.actionNewsFeedFragmentToArticleFragment(article)
            findNavController().navigate(action)
        }
    }

    private fun setupSearch() {
        with(binding) {
            searchImageButton.setOnClickListener {
                newsViewModel.searchNews(query = searchQueryTextView.text.toString())
                searchQueryTextView.clearTextAndFocus()
            }
            searchQueryTextView.addTextChangedListener { text ->
                text?.let {
                    if (it.isNotEmpty()) {
                        closeImageButton.show()
                    } else {
                        closeImageButton.hide()
                    }
                }
            }
            searchQueryTextView.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    newsViewModel.searchNews(query = searchQueryTextView.text.toString())
                    searchQueryTextView.clearTextAndFocus()
                    return@setOnKeyListener true
                }
                false
            }
            closeImageButton.setOnClickListener {
                searchQueryTextView.clearTextAndFocus()
            }
        }
    }

    private fun setupCategory() {
        with(binding) {
            generalChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getNews() }
            technologyChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "technology") }
            scienceChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "science") }
            healthChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "health") }
            businessChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "business") }
            sportsChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "sports") }
            entertainmentChip.setOnCheckedChangeListener { _, isChecked -> if (isChecked) newsViewModel.getCategory(category = "entertainment") }
        }
    }

    private fun setupThemeChanger() {
        context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK).run {
            when(this) {
                Configuration.UI_MODE_NIGHT_YES -> binding.themeImageButton.setImageResource(R.drawable.ic_light)
                Configuration.UI_MODE_NIGHT_NO -> binding.themeImageButton.setImageResource(R.drawable.ic_night)
            }
            binding.themeImageButton.setOnClickListener {
                when(this) {
                    Configuration.UI_MODE_NIGHT_YES -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }
    }
}