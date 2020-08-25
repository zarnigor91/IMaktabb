package com.example.imaktab.continuation_general.general

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_class_schedule.view.rv_child_schedule
import kotlinx.android.synthetic.main.item_class_schedule.view.tv_schedule_day
import kotlinx.android.synthetic.main.item_class_schedule.view.tv_schedule_month
import kotlinx.android.synthetic.main.item_class_schedule.view.tv_weekOfName
import kotlinx.android.synthetic.main.item_general_schedule.view.*
import java.text.SimpleDateFormat
import java.util.*

class WeekContinuationAdapter(val list: List<DailyContinuationModel>, val context: Context) :
    RecyclerView.Adapter<WeekContinuationAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var mylist = mutableListOf<DailyContinuationModel>()
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
            .inflate(R.layout.item_general_schedule, parent, false)
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

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        val format = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
        fun onBind(item: DailyContinuationModel) {
            itemView.apply {
            tv_weekOfName.text = context.getString(item.dayName)
            if (item.lessons.isNotEmpty()) {
                    val smdf = SimpleDateFormat("yyyy-MM-dd")
                    val date: Date = smdf.parse(item.lessons[0].date)
                when (val day = DateFormat.format("dd", date) as String) {
                        "01" -> tv_schedule_day.text = "1"
                        "02" -> tv_schedule_day.text = "2"
                        "03" -> tv_schedule_day.text = "3"
                        "04" -> tv_schedule_day.text = "4"
                        "05" -> tv_schedule_day.text = "5"
                        "06" -> tv_schedule_day.text = "6"
                        "07" -> tv_schedule_day.text = "7"
                        "08" -> tv_schedule_day.text = "8"
                        "09" -> tv_schedule_day.text = "9"
                        else -> tv_schedule_day.text = day
                    }
                    tv_not_found_general.visibility = View.GONE
                    rv_child_schedule.visibility = View.VISIBLE

                    val childLayoutManager =
                        LinearLayoutManager(rv_child_schedule.context, RecyclerView.VERTICAL, false)
                    rv_child_schedule.apply {
                        layoutManager = childLayoutManager
                        adapter = GeneralChildAdapter(item.lessons)

                        setRecycledViewPool(viewPool)
                    }
                    val months = (DateFormat.format("MM", date) as String).toInt()
                    tv_schedule_month.text = monthList[months - 1]
                } else {
                    rv_child_schedule.visibility = View.GONE
                    tv_not_found_general.visibility = View.VISIBLE
                    tv_schedule_day.text = (item.date.dayOfMonth).toString()
                    tv_schedule_month.text = monthList[item.date.monthValue - 1]
                }
            }
        }
    }

    fun updateList(newlist: List<DailyContinuationModel>) {
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }
}