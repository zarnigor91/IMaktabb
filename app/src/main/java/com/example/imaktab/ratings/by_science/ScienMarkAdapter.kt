package com.example.imaktab.ratings.by_science

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import com.example.imaktab.continuation_general.sciences.SciencesModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_week_mark.view.*

class ScienMarkAdapter(val list: List<MarkSciensModel>):RecyclerView.Adapter<ScienMarkAdapter.VH>() {
    private var myList:MutableList<MarkSciensModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
     val view=LayoutInflater.from(parent.context).inflate(R.layout.item_week_mark,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int =myList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
          val item=myList[position]
        holder.onBind(item,position)
    }

    inner class VH(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!),LayoutContainer{
        fun onBind(item:MarkSciensModel, position: Int) {
            if (position%2==1){
                itemView.liner_week_item.setBackgroundColor(Color.parseColor("#FBFBFD"))
            }
            else
            {
                itemView.liner_week_item.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            itemView.tv_week_num.text = (position+1).toString()
            itemView.tv_week_subject.text = item.date

            if (item.homeworkMark=="")
            {
                itemView.tv_week_mark.text = item.homeworkMark
            }
            else {
                if (item.homeworkMark?.toInt() == 5 && item.homeworkMark?.toInt() == 4) {
                    itemView.tv_week_mark.setTextColor(Color.parseColor("#05C46B"))
                    itemView.tv_week_mark.text = item.homeworkMark
                } else
                    if (item.homeworkMark?.toInt() == 3) {
                        itemView.tv_week_mark.setTextColor(Color.parseColor("#D3AB1A"))
                        itemView.tv_week_mark.text = item.homeworkMark
                    } else
                        if (item.homeworkMark?.toInt() == 2) {
                            itemView.tv_week_mark.setTextColor(Color.parseColor("#FA4A70"))
                            itemView.tv_week_mark.text = item.homeworkMark
                        } else {
                            itemView.tv_week_mark.setTextColor(Color.parseColor("#000000"))
                            itemView.tv_week_mark.text = item.homeworkMark
                        }
            }
            if (item.mark=="")
            {
                itemView.tv_subject_mark.text = item.mark
            }
            else {
                if (item.mark?.toInt() == 5 && item.mark?.toInt() == 4) {
                    itemView.tv_subject_mark.setTextColor(Color.parseColor("#05C46B"))
                    itemView.tv_subject_mark.text = item.mark
                } else
                    if (item.mark?.toInt() == 3) {
                        itemView.tv_subject_mark.setTextColor(Color.parseColor("#D3AB1A"))
                        itemView.tv_subject_mark.text = item.mark
                    } else
                        if (item.mark?.toInt() == 2) {
                            itemView.tv_subject_mark.setTextColor(Color.parseColor("#FA4A70"))
                            itemView.tv_subject_mark.text = item.mark
                        } else {
                            itemView.tv_subject_mark.setTextColor(Color.parseColor("#000000"))
                            itemView.tv_subject_mark.text = item.mark
                        }
            }
        }


    }
    fun updateList(newlist: List<MarkSciensModel>){
        myList.clear()
        myList.addAll(newlist)
        this.notifyDataSetChanged()
    }
}