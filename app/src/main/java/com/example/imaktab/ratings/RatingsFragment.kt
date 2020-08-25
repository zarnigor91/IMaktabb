package com.example.imaktab.ratings

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.LayoutDirection
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.example.imaktab.App
import com.example.imaktab.PARENT_ID_KEY
import com.example.imaktab.R
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.home_work.HomePagerAdapter
import com.example.imaktab.profile.ProfileFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.home_work_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.week_of_rating_layout.*

class RatingsFragment :Fragment(R.layout.week_of_rating_layout),IRatingsView{
    private var tabweekRating: TabLayout? = null
    private var vpWeekRating: ViewPager2? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabweekRating = view.findViewById(R.id.tab_science_rating) as TabLayout
        vpWeekRating = view.findViewById(R.id.vp_weekly) as ViewPager2
        vpWeekRating!!.setAdapter(RatingsAdapter(fragmentManager, lifecycle))
        TabLayoutMediator(tabweekRating!!, vpWeekRating!!, TabLayoutMediator.TabConfigurationStrategy{ tab, position ->
            when (position) {
                0 -> {tab.setText(R.string.week_ratings)}
                1 -> tab.setText(R.string.secience_ratings)

            }
        }).attach()

        tv_pupil_rating.setOnClickListener {
            if (App.getPupilList() != null && App.getPupilList()!!.size > 0) {
                showDialog()
            }
        }
        getPupils()

        im_ratings.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                ProfileFragment()
            )?.commit()
        }
    }

    private fun updatePUPIL(
        list: List<PupilModel>,
        id: Int
    ) {
//        presenter.geteWekSchedule()
        tv_class_ratings.text = list[id].klass
        tv_pupil_rating.text = list[id].pupil
    }

    @SuppressLint("WrongConstant")
    private fun showDialog() {
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null)
        val mBuilder = AlertDialog.Builder(context!!).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1.0f
            gravity = Gravity.TOP
        }
        val list = App.getPupilList()
        val rb = arrayOfNulls<RadioButton>(list!!.size)
        for (i in 0 until list.size) {
            rb[i] = RadioButton(activity)
            rb[i]!!.text = list[i].pupil
            rb[i]!!.id = i
            rb[i]!!.setPadding(15, 15, 15, 15)
            rb[i]!!.setTextSize(18F)
            rb[i]!!.layoutDirection = LayoutDirection.RTL
            rb[i]!!.gravity = Gravity.HORIZONTAL_GRAVITY_MASK
            rb[i]!!.layoutParams = params
            if (list[i].pupil_id == App.getCurrentPupilId()) {
                rb[i]!!.isChecked = true
            }
            mDialogView.pupil_rg.addView(rb[i])
        }
        mDialogView.bt_select.setOnClickListener {
            mAlertDialog.dismiss()
            val id = mDialogView.pupil_rg.checkedRadioButtonId
            App.setCurrentPupilId(list[id].pupil_id)
            updatePUPIL(list, id)

        }
    }
    private fun getPupils() {
        App.getPupilList()?.let { onGetPupilSuccess(it) }
        //presenter.getPupilListByParentId()
    }

    override fun parentId(): Long {
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val parentId = sharedPreferences.getLong(PARENT_ID_KEY, 0)
        return parentId
        return 1
    }
    override fun onGetPupilSuccess(pupilList: List<PupilModel>) {
        if (pupilList != null) {
            App.setPupilList(pupilList as MutableList)
            var currentPupilId = App.getCurrentPupilId()
            if (currentPupilId == null) {
                tv_pupil_rating.text = pupilList[0].pupil
                tv_class_ratings.text = pupilList[0].klass
                App.setCurrentPupilId(pupilList[0].pupil_id)
            } else {
                var flag = true
                for (i in 0..pupilList.size) {
                    if (currentPupilId == pupilList[i].pupil_id) {
                        tv_pupil_rating.text = pupilList[i].pupil
                        tv_class_ratings.text = pupilList[i].klass
                        flag = false
                        break
                    }
                }
                if (flag) {
                    tv_pupil_rating.text = pupilList[0].pupil
                    tv_class_ratings.text = pupilList[0].klass
                    App.setCurrentPupilId(pupilList[0].pupil_id)
                }
            }
        }
    }
}