package com.example.imaktab.continuation_general.general

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.general_fgragment.*
import kotlinx.android.synthetic.main.weekly_ratings_fragment.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.lang.String.format
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class GeneralFragment : BaseFragment(R.layout.general_fgragment),
    DatePickerDialog.OnDateSetListener, GeneralView {
    private val presenter: IGeneralPresenter = GeneralPresenterImple(this)
    private var adapter: WeekContinuationAdapter? = null
    private var dateGeneral: String? = null
    private val list = ArrayList<DailyContinuationModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        tv_conti_data.setOnClickListener {
            showDatePicker()
        }
    }

    override fun onStart() {
        super.onStart()
        presenter.getContinuationWeek(LocalDate.now().with(DayOfWeek.MONDAY))
    }

    private fun init() {
        adapter = WeekContinuationAdapter(listOf(), context!!)
        rv_continuation.layoutManager = LinearLayoutManager(this@GeneralFragment.context)
        rv_continuation.adapter = adapter
    }

    override fun getWeekResponce(list: ArrayList<DailyContinuationModel>) {
        this.list.clear()
        this.list.addAll(list)
        adapter?.updateList(this.list)
    }

    override fun getDate(): String {
        dateGeneral = tv_conti_data.text.toString()
        return dateGeneral.toString()
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
        val list: MutableList<Calendar> = LinkedList()
        val daysArray: Array<Calendar?>
        val cAux = Calendar.getInstance()
        while (cAux.timeInMillis <= gc.timeInMillis) {
            if (cAux[Calendar.DAY_OF_WEEK] !== 1 && cAux[Calendar.DAY_OF_WEEK] !== 3 && cAux[Calendar.DAY_OF_WEEK] !== 4 && cAux[Calendar.DAY_OF_WEEK] !== 5
                && cAux[Calendar.DAY_OF_WEEK] !== 6 && cAux[Calendar.DAY_OF_WEEK] !== 7
            ) {
                val c = Calendar.getInstance()
                c.timeInMillis = cAux.timeInMillis
                list.add(c)
            }
            cAux.timeInMillis = cAux.timeInMillis + 24 * 60 * 60 * 1000
        }
        daysArray = arrayOfNulls(list.size)
        for (i in daysArray.indices) {
            daysArray[i] = list[i]
        }
        dpd.selectableDays = daysArray
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, monthOfYear+1, dayOfMonth)
        tv_conti_data.text ="${String.format("%02d", dayOfMonth)}-${String.format("%02d", monthOfYear+1)}-$year"
        presenter.getContinuationWeek(date)
    }
}