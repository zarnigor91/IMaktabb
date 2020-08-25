package com.example.imaktab.home_work.passed

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import com.example.imaktab.ratings.by_week.DailyWeekMarkModel
import com.example.imaktab.ratings.by_week.WeekChildMarkAdapter
import kotlinx.android.synthetic.main.item_week_mark_parent.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class WeekPArentMarkAdapter(val list: List<DailyWeekMarkModel>, val context: Context) :
    RecyclerView.Adapter<WeekPArentMarkAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var mylist = ArrayList<DailyWeekMarkModel>()
    private val monthList: Array<String> = arrayOf(
        context.getString(R.string.januar),
        context.getString(R.string.februare),
        context.getString(R.string.march),
        context.getString(R.string.april),
        context.getString(R.string.may),
        context.getString(R.string.june),
        context.getString(R.string.july),
        context.getString(R.string.august),
        context.getString(R.string.september),
        context.getString(R.string.october),
        context.getString(R.string.november),
        context.getString(R.string.december)
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_week_mark_parent, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mylist.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.onBind(mylist[position])
    }

    inner class ViewHolder(val containerView: View) :
        RecyclerView.ViewHolder(containerView){
        val format = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
        fun onBind(item: DailyWeekMarkModel) {
            itemView.apply {
                tv_dayofweek_week.text = context.getString(item.dayName)
                if (item.lessons.isNotEmpty()) {
                    val smdf = SimpleDateFormat("yyyy-MM-dd")
                    val date: Date = smdf.parse(item.lessons[0].date)
                    when (val day = DateFormat.format("dd", date) as String) {
                        "01" -> tv_day_week.text = "1"
                        "02" -> tv_day_week.text = "2"
                        "03" -> tv_day_week.text = "3"
                        "04" -> tv_day_week.text = "4"
                        "05" -> tv_day_week.text = "5"
                        "06" -> tv_day_week.text = "6"
                        "07" -> tv_day_week.text = "7"
                        "08" -> tv_day_week.text = "8"
                        "09" -> tv_day_week.text = "9"
                        else -> tv_day_week.text = day
                    }
                    tv_not_found_lesson.visibility = View.GONE
                    rv_mark_week_parent.visibility = View.VISIBLE

                    val childLayoutManager =
                        LinearLayoutManager(
                            rv_mark_week_parent.context,
                            RecyclerView.VERTICAL,
                            false
                        )
                    rv_mark_week_parent.apply {
                        layoutManager = childLayoutManager
                        adapter = WeekChildMarkAdapter(item.lessons)
                        setRecycledViewPool(viewPool)
                    }
                    val months = (DateFormat.format("MM", date) as String).toInt()
                    tv_month_week.text = monthList[months - 1]
                } else {
                    rv_mark_week_parent.visibility = View.GONE
                    tv_not_found_lesson.visibility = View.VISIBLE
                    tv_day_week.text = (item.date.dayOfMonth).toString()
                    tv_month_week.text = monthList[item.date.monthValue - 1]
                }
            }
        }
    }

    fun updateList(newlist: List<DailyWeekMarkModel>) {
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }
}