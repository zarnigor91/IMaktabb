package com.example.imaktab.classScheduleFragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.example.imaktab.*
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.profile.ProfileFragment
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.dialog_layout.view.*
import kotlinx.android.synthetic.main.lesson_fragment.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.*
import kotlin.collections.ArrayList


class ClassScheduleFragment : BaseFragment(R.layout.lesson_fragment), ClassScheduleView,DatePickerDialog.OnDateSetListener  {

    private var adapter: WeeklyLessonsAdapter? = null
    private val presenter: ChildSchedulePresenterImple = ChildSchedulePresenterImple(this)
    private val list = ArrayList<DailyLessonsModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        tv_monday.setOnClickListener {
            showDatePicker()
        }
        getPupils()
        tv_user.setOnClickListener {
            if (App.getPupilList() != null && App.getPupilList()!!.isNotEmpty()) {
                showDialog()
            }

        }
        im_lesson.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                ProfileFragment()
            )?.commit()
        }
    }

    private fun init() {
        adapter = WeeklyLessonsAdapter(context!!, listOf())
        rv_week_sche.adapter = adapter
    }

    private fun getPupils() {
        App.getPupilList()?.let { onGetPupilSuccess(it) }
        //presenter.getPupilListByParentId()

    }

    override fun onSuccess() {

    }

    override fun onError() {

    }

    override fun getResponsValue(list: ArrayList<DailyLessonsModel>) {
        this.list.clear()
        this.list.addAll(list)
        adapter?.updateList(this.list)
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
        for (i in list.indices) {
            rb[i] = RadioButton(activity)
            rb[i]!!.text = list[i].pupil
            rb[i]!!.id = i
            rb[i]!!.setPadding(15, 15, 15, 15)
            rb[i]!!.textSize = 18F
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
        presenter.geteWekSchedule(LocalDate.now().with(DayOfWeek.MONDAY))
        tv_class_lessonfr.text = list[id].klass
        tv_user.text = list[id].pupil
    }

    override fun parentId(): Long {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getLong(PARENT_ID_KEY, 0)
    }

    override fun onGetPupilSuccess(pupilList: List<PupilModel>) {
        if (pupilList.isNotEmpty()) {
            App.setPupilList(pupilList as MutableList)
            val currentPupilId = App.getCurrentPupilId()
            if (currentPupilId == null) {
                tv_user.text = pupilList[0].pupil
                tv_class_lessonfr.text = pupilList[0].klass
                App.setCurrentPupilId(pupilList[0].pupil_id)
            } else {
                var flag = true
                for (i in 0..pupilList.size) {
                    if (currentPupilId == pupilList[i].pupil_id) {
                        tv_user.text = pupilList[i].pupil
                        tv_class_lessonfr.text = pupilList[i].klass
                        flag = false
                        break
                    }
                }
                if (flag) {
                    tv_user.text = pupilList[0].pupil
                    tv_class_lessonfr.text = pupilList[0].klass
                    App.setCurrentPupilId(pupilList[0].pupil_id)
                }
            }
            presenter.geteWekSchedule(LocalDate.now().with(DayOfWeek.MONDAY))
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val dpd =
            DatePickerDialog.newInstance(
                this,
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
        dpd.show(activity!!.fragmentManager, "DatePickerDialog")

        val g1 = GregorianCalendar()
        g1.add(Calendar.DATE,1)
        g1.add(Calendar.DATE,3)
        g1.add(Calendar.DATE,4)
        g1.add(Calendar.DATE,5)
        g1.add(Calendar.DATE,6)
        g1.add(Calendar.DATE,7)
        val gc = GregorianCalendar()
        gc.add(Calendar.DAY_OF_MONTH, 30)
        val dayslist: MutableList<Calendar> = LinkedList()
        val daysArray: Array<Calendar?>
        val cAux = Calendar.getInstance()
        while (cAux.timeInMillis <= gc.timeInMillis) {
            if (cAux[Calendar.DAY_OF_WEEK] !== 1 && cAux[Calendar.DAY_OF_WEEK] !== 3  && cAux[Calendar.DAY_OF_WEEK] !== 4 &&  cAux[Calendar.DAY_OF_WEEK] !==5
                && cAux[Calendar.DAY_OF_WEEK] !==6 && cAux[Calendar.DAY_OF_WEEK] !==7
            ) {
                val c = Calendar.getInstance()
                c.timeInMillis = cAux.timeInMillis
                dayslist.add(c)
            }
            cAux.timeInMillis = cAux.timeInMillis + 24 * 60 * 60 * 1000
        }
        daysArray = arrayOfNulls(dayslist.size)
        for (i in daysArray.indices) {
            daysArray[i] = dayslist[i]
        }
        dpd.selectableDays = daysArray
    }

    private fun dateToCalendar(date: Date): Calendar? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return calendar
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearRequest()
    }

    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, monthOfYear+1, dayOfMonth)
        tv_monday.text = "${String.format("%02d", dayOfMonth)}-${String.format("%02d", monthOfYear+1)}-$year"
        presenter.geteWekSchedule(date)
    }

}