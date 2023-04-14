package com.bagas.project.filmbase.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bagas.project.filmbase.ui.favorite.FavoriteFragment
import com.bagas.project.filmbase.ui.favorite.FavoriteMoviesFragment
import com.bagas.project.filmbase.ui.favorite.FavoriteTvshowsFragment

class FavoriteSectionsPagerAdapter(activity: Fragment): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return FavoriteFragment.TAB_TITLES.size
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = FavoriteMoviesFragment()
            else -> fragment = FavoriteTvshowsFragment()
        }

        return fragment
    }
}