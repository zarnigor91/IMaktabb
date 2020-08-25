package com.example.imaktab.home_work.passed

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_inner_class_schelude.view.*
import kotlinx.android.synthetic.main.item_science_evaluation.view.*

class LessonMarkAdapter(val list: List<LessonMarkModel>):RecyclerView.Adapter<LessonMarkAdapter.VH>(){

    private var mylist: MutableList<LessonMarkModel> = list as MutableList<LessonMarkModel>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_science_evaluation, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mylist[position]
        holder.onBind(item, position)
    }

    inner class VH(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun onBind(item: LessonMarkModel, position: Int) {
            if (position%2==1){
                itemView.liner_les_item.setBackgroundColor(Color.parseColor("#FBFBFD"))
            }
            else
            {
                itemView.liner_les_item.setBackgroundColor(Color.parseColor("#FFFFFF"))
            }
            itemView.tv_les_num.text = (position+1).toString()
            itemView.tv_les_subject.text = item.subject
            if (item.confirmed==true)
            itemView.tv_les_check.setImageResource(R.drawable.ic_check_icon)
            else
            {
                itemView.tv_les_check.setImageResource(R.drawable.ic_check_false)
            }

            if (item.homeworkMark=="")
            {
                itemView.tv_les_mark.text = item.homeworkMark
            }
            else {
                if (item.homeworkMark.toInt() == 5 && item.homeworkMark.toInt() == 4) {
                    itemView.tv_les_mark.setTextColor(Color.parseColor("#05C46B"))
                    itemView.tv_les_mark.text = item.homeworkMark
                } else
                    if (item.homeworkMark.toInt() == 3) {
                        itemView.tv_les_mark.setTextColor(Color.parseColor("#D3AB1A"))
                        itemView.tv_les_mark.text = item.homeworkMark
                    } else
                        if (item.homeworkMark.toInt() == 2) {
                            itemView.tv_les_mark.setTextColor(Color.parseColor("#FA4A70"))
                            itemView.tv_les_mark.text = item.homeworkMark
                        } else {
                            itemView.tv_les_mark.setTextColor(Color.parseColor("#000000"))
                            itemView.tv_les_mark.text = item.homeworkMark
                        }
            }

        }
    }

    fun updateList(newlist: List<LessonMarkModel>) {
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }
}