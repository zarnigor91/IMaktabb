package com.example.imaktab.ratings.by_week

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.example.imaktab.home_work.passed.WeekPArentMarkAdapter
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.weekly_ratings_fragment.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class WeeklyRatingsFragment : BaseFragment(R.layout.weekly_ratings_fragment), IWeekMarkView,
    DatePickerDialog.OnDateSetListener {
    private var adapterWeek: WeekPArentMarkAdapter? = null
    private val presenter: IWeekMarkPresenter = WeekMarkPresenterImple(this)
    var list = ArrayList<DailyWeekMarkModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        tv_date_week_rating.setOnClickListener {
            showDatePicker()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.getWeekMark(LocalDate.now().with(DayOfWeek.MONDAY))
    }

    private fun init() {
        adapterWeek = WeekPArentMarkAdapter(list, context!!)
        rv_week_mark.layoutManager = LinearLayoutManager(context)
        rv_week_mark.adapter = adapterWeek
    }

    override fun getWeekvalue(list: ArrayList<DailyWeekMarkModel>) {
        this.list.clear()
        this.list.addAll(list)
        adapterWeek?.updateList(this.list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearRequest()
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
        g1.add(Calendar.DATE, 1)
        g1.add(Calendar.DATE, 3)
        g1.add(Calendar.DATE, 4)
        g1.add(Calendar.DATE, 5)
        g1.add(Calendar.DATE, 6)
        g1.add(Calendar.DATE, 7)
        val gc = GregorianCalendar()
        gc.add(Calendar.DAY_OF_MONTH, 30)
        val dayslist: MutableList<Calendar> = LinkedList()
        val daysArray: Array<Calendar?>
        val cAux = Calendar.getInstance()
        while (cAux.timeInMillis <= gc.timeInMillis) {
            if (cAux[Calendar.DAY_OF_WEEK] !== 1 && cAux[Calendar.DAY_OF_WEEK] !== 3 && cAux[Calendar.DAY_OF_WEEK] !== 4 && cAux[Calendar.DAY_OF_WEEK] !== 5
                && cAux[Calendar.DAY_OF_WEEK] !== 6 && cAux[Calendar.DAY_OF_WEEK] !== 7
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
    @SuppressLint("SetTextI18n")
    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, monthOfYear+1, dayOfMonth)
        tv_date_week_rating.text = "${String.format("%02d", dayOfMonth)}-${String.format("%02d", monthOfYear+1)}-$year"
        presenter.getWeekMark(date)
    }
}