package com.bagas.project.filmbase.ui.search

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchSectionPagerAdapter(activity: SearchFragment): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return SearchFragment.TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = MovieSearchedFragment()
            else -> fragment = TvshowSearchedFragment()
        }

        return fragment
    }
}