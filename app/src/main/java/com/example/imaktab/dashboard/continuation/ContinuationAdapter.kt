package com.example.imaktab.dashboard.continuation

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.table_list_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ContinuationAdapter(list: List<SceduleModel>):RecyclerView.Adapter<ContinuationAdapter.VH>()
{
private var mylist:MutableList<SceduleModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
       val view =LayoutInflater.from(parent.context).inflate(R.layout.table_list_item,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int=mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
       val item=mylist[position]
        holder.onBind(item)
    }
    inner class VH(override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer {

        fun onBind(item: SceduleModel) {
            itemView.tv_number.text = (position+1).toString()
            itemView.tv_subject.text = item.subject
            itemView.tv_teacher.text = item.teacher
            itemView.tv_room.text = item.room
            itemView.tv_time_start.text = item.start
            itemView.tv_time_end.text = item.end
            if (position%2==0){
            itemView.liner_dashboard.setBackgroundColor(Color.parseColor("#FBFBFD"))}
            else{
                itemView.liner_dashboard.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }

            val currentDate = Calendar.getInstance().time.time
            val format = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss")
            val lessonStartDate = format.parse(item.date+"_"+item.start).time
            val lessonEndDate = format.parse(item.date+"_"+item.end).time
            if(currentDate>lessonStartDate && currentDate<lessonEndDate){
                itemView.iv_status.setImageResource(R.drawable.ic_clock)
                itemView.tv_subject.setTextColor(Color.parseColor("#05C46B"))
            }else if(currentDate<lessonStartDate){
                itemView.iv_status.setImageResource(R.drawable.ic_minus)
            }else{
                if (item.att==true)
                { itemView.iv_status.setImageResource(R.drawable.ic_true_icon_active)}
                else
                {
                    itemView.iv_status.setImageResource(R.drawable.ic_false_icon)
                }
            }
        }
    }

    fun updateList(newlist: List<SceduleModel>){
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }


}