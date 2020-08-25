package com.example.imaktab.dashboard.continuation

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.table_list_item_rating.view.*

class MarksAdapter(list: List<SceduleModel>) : RecyclerView.Adapter<MarksAdapter.VH>() {

    private var mylist: MutableList<SceduleModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.table_list_item_rating, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = mylist[position]
        holder.onBind(item)
    }

    inner class VH(override val containerView: View?) :
        RecyclerView.ViewHolder(containerView!!), LayoutContainer {
        fun onBind(item: SceduleModel) {
            itemView.tv_number_rating.text = (adapterPosition + 1).toString()
            itemView.tv_rating.text = item.mark
            itemView.tv_subject_rating.text = item.subject

            itemView.bg_item_rating.setBackgroundColor(Color.parseColor(if (adapterPosition % 2 == 0) "#FFFFFF" else "#FBFBFD"))
            var mark = 0

            try {
                mark = item.mark?.toInt()!!
            } catch (e: Exception) {
            }
            itemView.tv_rating.text = item.mark

            itemView.tv_rating.setTextColor(
                getColor(
                    itemView.context, when (mark) {
                        5 -> R.color.colorGreen
                        4 -> R.color.colorGreen
                        3 -> R.color.colorYellow
                        2 -> R.color.colorRed
                        else -> R.color.colorBlack
                    }
                )
            )
        }
    }

    fun updateList(list: List<SceduleModel>) {
        mylist.clear()
        mylist.addAll(list)
        this.notifyDataSetChanged()
    }
}