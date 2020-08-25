package com.example.imaktab.ratings

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imaktab.ratings.by_science.ScienceRatingsFragment
import com.example.imaktab.ratings.by_week.WeeklyRatingsFragment

class RatingsAdapter(fm: FragmentManager?, lifecycle: Lifecycle): FragmentStateAdapter(fm!!,lifecycle){
    override fun getItemCount(): Int=2

    override fun createFragment(position: Int): Fragment {

        if (position==0){
            val fragment1=
                WeeklyRatingsFragment()

            return fragment1
        }
        else
        { val fragment=
            ScienceRatingsFragment()
            return fragment
        }
    }

}
