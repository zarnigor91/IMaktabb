package com.example.imaktab.dashboard
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imaktab.R
import com.example.imaktab.classScheduleFragment.ClassScheduleFragment
import com.example.imaktab.continuation_general.GeneralContinuationFragment
import com.example.imaktab.home_work.HomeWorkFragment
import com.example.imaktab.ratings.RatingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.dashboard_activity.*


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_activity)
        navigation.setOnNavigationItemSelectedListener(bottomNavigationListener)
        val fragment =DashboardFragment()
        if(savedInstanceState==null){
            addFragment(fragment)
        }


    }
    private val bottomNavigationListener= BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId){
            R.id.main ->{
                val fragment = DashboardFragment()
                addFragment(fragment)
                it.setIcon(R.drawable.ic_mainn)
                return@OnNavigationItemSelectedListener true
            }
            R.id.table ->{
                val fragment = ClassScheduleFragment()
                addFragment(fragment)
            }
            R.id.home_work ->{
                val fragment = HomeWorkFragment()
                addFragment(fragment)
            }
            R.id.continuation ->{
                val fragment =GeneralContinuationFragment()
                addFragment(fragment)
            }
            R.id.rating ->{
                val fragment =RatingsFragment()
                addFragment(fragment)
            }
        }
        true
    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        val current = supportFragmentManager.findFragmentById(R.id.container)
        when(current) {
            is DashboardFragment-> {
                super.onBackPressed()
            }
            else -> {
                addFragment(DashboardFragment())
            }
        }
    }


}
