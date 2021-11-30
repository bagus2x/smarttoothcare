package com.psi.smarttoothcare.ui.article

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.psi.smarttoothcare.databinding.FragmentArticleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel by activityViewModels<ArticleViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFactsRecyclerView()
        setupTipsViewPager2()
    }

    private fun setupFactsRecyclerView() {
        val factsAdapter = ArticleFactsAdapter()
        val factsLayoutManager = LinearLayoutManager(requireContext())

        factsAdapter.setOnItemClickListener {
            val action = ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(it.url)
            findNavController().navigate(action)
        }

        articleViewModel.facts.observe(viewLifecycleOwner) {
            factsAdapter.differ.submitList(it)
        }

        binding.rvFacts.apply {
            adapter = factsAdapter
            layoutManager = factsLayoutManager
        }
    }

    private fun setupTipsViewPager2() {
        val tipsAdapter = ArticleTipsAdapter()

        tipsAdapter.setOnItemClickListener {
            val action = ArticleFragmentDirections.actionArticleFragmentToArticleDetailFragment(it.url)
            findNavController().navigate(action)
        }

        articleViewModel.facts.observe(viewLifecycleOwner) {
            tipsAdapter.differ.submitList(it)
        }

        binding.vpTips.apply {
            adapter = tipsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}