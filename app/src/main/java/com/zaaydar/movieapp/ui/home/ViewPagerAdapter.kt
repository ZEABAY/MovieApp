package com.zaaydar.movieapp.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zaaydar.movieapp.ui.home.latest.LatestFragment
import com.zaaydar.movieapp.ui.home.popular.PopularFragment
import com.zaaydar.movieapp.ui.home.recommended.RecommendedFragment
import com.zaaydar.movieapp.ui.home.upcoming.UpcomingFragment

class ViewPagerAdapter constructor(
    private val tabNames: Array<String>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PopularFragment()
            1 -> LatestFragment()
            2 -> UpcomingFragment()
            3 -> RecommendedFragment()
            else -> PopularFragment()
        }
    }

    override fun getItemCount(): Int {
        return tabNames.size
    }
}