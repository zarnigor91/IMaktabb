package com.example.imaktab.classScheduleFragment

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_class_schedule.view.*
import kotlinx.android.synthetic.main.item_inner_class_schelude.view.*
import kotlinx.android.synthetic.main.table_list_item.view.*
import kotlinx.android.synthetic.main.table_list_item.view.tv_number
import kotlinx.android.synthetic.main.table_list_item.view.tv_room
import kotlinx.android.synthetic.main.table_list_item.view.tv_subject
import kotlinx.android.synthetic.main.table_list_item.view.tv_teacher
import kotlinx.android.synthetic.main.table_list_item.view.tv_time_end
import kotlinx.android.synthetic.main.table_list_item.view.tv_time_start
import java.text.SimpleDateFormat
import java.util.*

class DailyLessonsAdapter(val list: List<LessonModel>) :
    RecyclerView.Adapter<DailyLessonsAdapter.VH>() {

    private var mylist: MutableList<LessonModel> = list as MutableList<LessonModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inner_class_schelude, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mylist[position]
        holder.onBind(item, position)
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun onBind(item: LessonModel, position: Int) {
            if (position%2==0){
                itemView.liner_week_day.setBackgroundColor(Color.parseColor("#FBFBFD"))
            }
            else
            {
                itemView.liner_week_day.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            itemView.tv_number.text = (position+1).toString()
            itemView.tv_subject.text = item.subject
            itemView.tv_teacher.text = item.teacher
            itemView.tv_room.text = item.room
            itemView.tv_time_start.text = item.start
            itemView.tv_time_end.text = item.end
        }
    }

    fun updateList(newlist: List<LessonModel>) {
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }


}