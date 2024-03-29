package com.bagas.project.filmbase.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bagas.project.filmbase.R
import com.bagas.project.filmbase.ui.SectionsPagerAdapter
import com.bagas.project.filmbase.databinding.FragmentHomeBinding
import com.dicoding.bfaa.mytablayout.animation.ZoomOutPageTransformer
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        val TAB_TITLES = intArrayOf(
            R.string.tab_text_movie,
            R.string.tab_text_tvshow
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupTabLayoutWithViewPager()
        val root: View = binding.root

        return root
    }

    private fun setupTabLayoutWithViewPager() {
        binding.viewPager.adapter = SectionsPagerAdapter(this)
        binding.viewPager.setPageTransformer(ZoomOutPageTransformer())
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        for (i in 0..1) {
            val textView = LayoutInflater.from(requireContext()).inflate(R.layout.tab_title, null) as TextView
            binding.tabLayout.getTabAt(i)?.customView = textView
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}