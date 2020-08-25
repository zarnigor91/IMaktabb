package com.example.imaktab.dashboard

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.format.DateFormat
import android.util.LayoutDirection
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.PreferenceManager
import com.example.imaktab.App
import com.example.imaktab.BaseFragment
import com.example.imaktab.PARENT_ID_KEY
import com.example.imaktab.R
import com.example.imaktab.dashboard.continuation.*
import com.example.imaktab.profile.ProfileFragment
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.SimpleDateFormat
import java.util.*


class DashboardFragment : BaseFragment(R.layout.main_fragment), ContinuationView {
    private var adapter: ContinuationAdapter? = null
    private var marksAdapter: MarksAdapter? = null
    private val presenter: ContinuationPresenterImple =
        ContinuationPresenterImple(
            this
        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getPupils()
        tv_pupil_dashboard.setOnClickListener {
            if (App.getPupilList() != null && App.getPupilList()!!.size > 0) {
                showDialog()
            }
        }

        im_user_dash.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                ProfileFragment()
            )?.commit()
        }
    }// login qimay turing terib qo'ying lk log passni hoz

    private fun init() {
        adapter =
            ContinuationAdapter(listOf())
        marksAdapter =
            MarksAdapter(listOf())
        rv_lesson.adapter = adapter
        rv_rating.adapter = marksAdapter
    }

    private fun getPupils() {
        showProgressDialog()
        presenter.getPupilListByParentId()
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

    private fun updatePUPIL(
        list: List<PupilModel>,
        id: Int
    ) {
        presenter.getScheduleTodayByPupilId()
        tv_class.text = list[id].klass
        tv_pupil_dashboard.text = list[id].pupil
    }

    override fun onSuccess() {

    }

    override fun onError() {

    }



    override fun getResponsValue(continuationResponce: List<SceduleModel>) {
        val format = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
        adapter!!.updateList(continuationResponce)
        if(continuationResponce.isEmpty()){
            tv_item_not_found.visibility = View.VISIBLE
            rv_lesson.visibility = View.GONE
        }else{
            tv_item_not_found.visibility = View.GONE
            rv_lesson.visibility = View.VISIBLE
        }
        marksAdapter!!.updateList(continuationResponce)
        val sdf = SimpleDateFormat("dd-M-yyyy")
        val dateString = "22-03-2017"
        val calendar = Calendar.getInstance()
        val today = Calendar.getInstance().time
        Log.d("Sana","sana1"+today)
        val month = arrayOf(
            getResources().getString(R.string.januar),
            getResources().getString(R.string.februare),
            getResources().getString(R.string.march),
            getResources().getString(R.string.april),
            getResources().getString(R.string.may),
            getResources().getString(R.string.june),
            getResources().getString(R.string.july),
            getResources().getString(R.string.august),
            getResources().getString(R.string.september),
            getResources().getString(R.string.october),
            getResources().getString(R.string.november),
            getResources().getString(R.string.december)
        )

        Toast.makeText(activity, "Updated " + continuationResponce.size, Toast.LENGTH_SHORT).show()
        if (continuationResponce.isNotEmpty()) {
//            val smdf = SimpleDateFormat("yyyy-MM-dd")
//            val date: Date = smdf.parse(continuationResponce[0].date)
//

//            Log.d("Kun1","kun1"+date)
//            val day = DateFormat.format("dd", date) as String
            val calendar = Calendar.getInstance()

            val currentDayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            Log.d("Kun1","kun1"+currentDayOfMonth)
            val currentMonthOfYear = calendar[Calendar.MONTH]
            when(currentDayOfMonth.toString()){
                "01"->tv_day_scushel.text = "1"
                "02"->tv_day_scushel.text = "2"
                "03"->tv_day_scushel.text = "3"
                "04"->tv_day_scushel.text= "4"
                "05"->tv_day_scushel.text= "5"
                "06"->tv_day_scushel.text= "6"
                "07"->tv_day_scushel.text = "7"
                "08"->tv_day_scushel.text = "8"
                "09"->tv_day_scushel.text = "9"
                else ->tv_day_scushel.text = currentDayOfMonth.toString()
            }
//            val months = (DateFormat.format("MM", today) as String).toInt()
//            val monntss=Calendar.MONTH
            if (currentMonthOfYear > 0 && currentMonthOfYear < 13) {
                val uzmonth = month[currentMonthOfYear ]
                tv_month_scushel.text = uzmonth
            }

        }
        else
        {
            val calendar = Calendar.getInstance()

            val currentDayOfMonth = calendar[Calendar.DAY_OF_MONTH]
            Log.d("Kun1","kun1"+currentDayOfMonth)
            val currentMonthOfYear = calendar[Calendar.MONTH]
            when(currentDayOfMonth.toString()){
                "01"->tv_day_scushel.text = "1"
                "02"->tv_day_scushel.text = "2"
                "03"->tv_day_scushel.text = "3"
                "04"->tv_day_scushel.text= "4"
                "05"->tv_day_scushel.text= "5"
                "06"->tv_day_scushel.text= "6"
                "07"->tv_day_scushel.text = "7"
                "08"->tv_day_scushel.text = "8"
                "09"->tv_day_scushel.text = "9"
                else ->tv_day_scushel.text = currentDayOfMonth.toString()
            }
//            val months = (DateFormat.format("MM", today) as String).toInt()
//            val monntss=Calendar.MONTH
            if (currentMonthOfYear > 0 && currentMonthOfYear < 13) {
                val uzmonth = month[currentMonthOfYear]
                tv_month_scushel.text = uzmonth
            }

        }
        Toast.makeText(activity, "Updated " + continuationResponce.size, Toast.LENGTH_SHORT).show()

    }

    override fun onGetPupilSuccess(list: List<PupilModel>) {
        if (list.size < 1) {
            Toast.makeText(activity, "Pupils not found!", Toast.LENGTH_LONG).show()
        } else {
            App.setPupilList(list as MutableList)
            var currentPupilId = App.getCurrentPupilId()
            if (currentPupilId == null) {
                tv_pupil_dashboard.text = list[0].pupil
                tv_class.text = list[0].klass
                App.setCurrentPupilId(list[0].pupil_id)
            } else {
                var flag = true
                for (i in 0..list.size) {
                    if (currentPupilId == list[i].pupil_id) {
                        tv_pupil_dashboard.text = list[i].pupil
                        tv_class.text = list[i].klass
                        flag = false
                        break
                    }
                }
                if (flag) {
                    tv_pupil_dashboard.text = list[0].pupil
                    tv_class.text = list[0].klass
                    App.setCurrentPupilId(list[0].pupil_id)
                }
            }
            presenter.getTotal()
            presenter.getScheduleTodayByPupilId()
            presenter.getDashboardPupils()
        }
    }

    override fun parentId(): Long {
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val parentId = sharedPreferences.getLong(PARENT_ID_KEY, 0)//binrinchi kirgani da parent id 0 bo'layapti keyin 0 ni parent id qilib jonatyapti lk 0 parent bo'yaica nimadir yoq
        return parentId
    }// bilmadim unda biron yo`lini topaylik hozi topshirishim kkda iltimos mayli default qilsak ham unda hozirlikcha keyi

    override fun updateTotalGPA(totagpa: Total) {
        tvtotal.text = totagpa.total.toString()

    }

    override fun getDashboardValue(dashboardResponce: DashboardResponce) {
        tv_praises.text = dashboardResponce.praises.toString()
        Log.d("dsf", "kelmadi" + dashboardResponce.praises.toString())
        tv_passes.text = dashboardResponce.passes.toString()
        tv_behavior.text = dashboardResponce.behaviors.toString()
        tv_complaints.text =
            dashboardResponce.complaints.toString()
        hideProgressDialog()

    }

}