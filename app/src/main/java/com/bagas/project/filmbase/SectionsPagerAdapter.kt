package com.bagas.project.filmbase

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bagas.project.filmbase.ui.home.HomeFragment
import com.bagas.project.filmbase.ui.home.movie.MovieFragment
import com.bagas.project.filmbase.ui.home.tvshow.TvshowFragment

class SectionsPagerAdapter(activity: HomeFragment): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return HomeFragment.TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = MovieFragment()
            else -> fragment = TvshowFragment()
        }

        return fragment
    }
}