package com.dgituserapi2.view.adapter

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dgituserapi2.R
import com.dgituserapi2.view.fragment.DetailUserFragment
import com.dgituserapi2.view.fragment.RepositoryFragment

class ViewPagerAdapter(activity: AppCompatActivity, private val username: String?) :
        FragmentStateAdapter(activity) {

    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.repository, R.string.followers, R.string.followings)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RepositoryFragment.newInstance(username)
            else -> DetailUserFragment.newInstance(position, username)
        }
    }
}
