package com.example.imaktab.home_work.tomorrow_homeWork

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import com.example.imaktab.dashboard.continuation.SceduleModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.sciences_item.view.*

class TomorrowAdapter(
     list: List<TommorowModel>, private val action:
    (TommorowModel) -> Unit ):RecyclerView.Adapter<TomorrowAdapter.VH>(){
    private var mylist: MutableList<TommorowModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.sciences_item,parent,false)
            return VH(view,action)
    }

    override fun getItemCount(): Int=mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item=mylist[position]
        holder.onBind(item)
    }

    inner class VH(override val containerView: View,  private val action: (TommorowModel) -> Unit):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
           private var selectSubject:TommorowModel?=null
        init {
            itemView.setOnClickListener {
                action.invoke(mylist[adapterPosition])
            }
        }

        fun onBind(item: TommorowModel) {
            this.selectSubject=item
            if (item.confirmed==true)
            { itemView.subject_item_tomorrow.text=item.subject
                itemView.subject_item_tomorrow.setTextColor(itemView.getResources().getColor(R.color.colorGreen))
            }
            else{
                itemView.subject_item_tomorrow.text=item.subject
                itemView.subject_item_tomorrow.setTextColor(Color.parseColor("#3966FB"))
            }
        }

    }

    fun updateList(newlist: List<TommorowModel>,action: (TommorowModel) -> Unit) {
        mylist.clear()
        mylist.addAll(newlist)
        this.notifyDataSetChanged()
    }

}