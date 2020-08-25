package com.example.imaktab.home_work

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imaktab.home_work.passed.PastFragment
import com.example.imaktab.home_work.tomorrow_homeWork.TomorrowFragment

class HomePagerAdapter(fm: FragmentManager?, lifecycle: Lifecycle)
    :   FragmentStateAdapter(fm!!, lifecycle){
    override fun getItemCount(): Int =2
    override fun createFragment(position: Int): Fragment {

       if (position==0){
           val fragment1=
               TomorrowFragment()
        return fragment1
       }
        else
       { val fragment= PastFragment()
           return fragment
       }

    }

}