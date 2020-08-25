package com.example.imaktab.home_work.passed

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_day_science.view.*
import kotlinx.android.synthetic.main.item_week_mark_parent.view.*
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import java.text.SimpleDateFormat


import java.util.*
import kotlin.collections.ArrayList

class WeekLessonsMarkAdapter(val context: Context, val list: List<DailyLessonsMarkModel>) :
    RecyclerView.Adapter<WeekLessonsMarkAdapter.ViewHolder>() {
    private val viewPool = RecyclerView.RecycledViewPool()
    private var mylist = mutableListOf<DailyLessonsMarkModel>()
    private var t: Int = 0
    private val month: Array<String>
    var uzmonth = ""

    init {
        t = LocalDate.now().with(DayOfWeek.MONDAY).dayOfMonth
        t--
        month = arrayOf(
            context.resources.getString(R.string.januar),
            context.resources.getString(R.string.februare),
            context.resources.getString(R.string.march),
            context.resources.getString(R.string.april),
            context.resources.getString(R.string.may),
            context.resources.getString(R.string.june),
            context.resources.getString(R.string.july),
            context.resources.getString(R.string.august),
            context.resources.getString(R.string.september),
            context.resources.getString(R.string.october),
            context.resources.getString(R.string.november),
            context.resources.getString(R.string.december)
        )

        uzmonth = month[Calendar.MONTH]
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day_science, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mylist.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val parent = mylist[position]
        holder.onBind(parent)

        holder.itemView.tv_day_pass.text = (t).toString()

        if (parent.lessons.isNotEmpty()) {
            holder.tvNotPass.visibility = View.GONE
            holder.rvhomework.visibility = View.VISIBLE

            val childLayoutManager =
                LinearLayoutManager(holder.rvhomework.context, RecyclerView.VERTICAL, false)
            holder.rvhomework.apply {
                layoutManager = childLayoutManager
                adapter = LessonMarkAdapter(parent.lessons)
                setRecycledViewPool(viewPool)
            }
        } else {
            holder.rvhomework.visibility = View.VISIBLE
            holder.tvNotPass.visibility = View.GONE
        }
    }

    inner class ViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun onBind(item: DailyLessonsMarkModel) {
            itemView.tv_dayofweek_general.text = item.dayName

            if (item.lessons.isNotEmpty()) {
                val smdf = SimpleDateFormat("yyyy-MM-dd")
                for (c in item.date) {
                    val date: Date = smdf.parse(item.date)
                    Log.d("date", "sana" + item.date)
                    val day = DateFormat.format("dd", date) as String
                    when (day) {
                        "01" -> itemView.tv_day_pass.text = "1"
                        "02" -> itemView.tv_day_pass.text = "2"
                        "03" -> itemView.tv_day_pass.text = "3"
                        "04" -> itemView.tv_day_pass.text = "4"
                        "05" -> itemView.tv_day_pass.text = "5"
                        "06" -> itemView.tv_day_pass.text = "6"
                        "07" -> itemView.tv_day_pass.text = "7"
                        "08" -> itemView.tv_day_pass.text = "8"
                        "09" -> itemView.tv_day_pass.text = "9"
                        else -> itemView.tv_day_pass.text = day
                    }

                    val months = (DateFormat.format("MM", date) as String).toInt()
                    if (months > 0 && months < 13) {
                        val uzmonth = month[months - 1]
                        itemView.tv_month_pass.text = uzmonth
                    }
                }
            } else {
                itemView.tv_day_pass.text = (t).toString()
                itemView.tv_month_pass.text = uzmonth
                if (t == 31) {
                    t = 0
                    if (Calendar.MONTH > 0 && Calendar.MONTH < 13) {
                        uzmonth = month[Calendar.MONTH + 1]
                    }
                }
                t += 1
            }
        }
        val rvhomework: RecyclerView = itemView.rv_homeworks
        val tvNotPass: TextView = itemView.tv_not_found_week
    }


    fun updateList(newlist: List<DailyLessonsMarkModel>) {
        mylist.clear()
        mylist.addAll(newlist)
        t = LocalDate.now().with(DayOfWeek.MONDAY).dayOfMonth
        uzmonth = month[LocalDate.now().month.value-1]
        this.notifyDataSetChanged()
    }
}