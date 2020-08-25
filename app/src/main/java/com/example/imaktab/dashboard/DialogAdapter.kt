package com.example.imaktab.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.App
import com.example.imaktab.IMAKTAB
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_dioalog.view.*

class DialogAdapter(val mylist: List<PupilModel>):RecyclerView.Adapter<DialogAdapter.VH>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_dioalog,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int=mylist.size

    override fun onBindViewHolder(holder: VH, position: Int) {
       val item=mylist[position]
        holder.onBind(item)
    }
    inner class VH(override val containerView: View): RecyclerView.ViewHolder(containerView),LayoutContainer {

        fun onBind(item: PupilModel) {
            itemView.rb_pupillist_item.text = item.pupil
            if(App.getCurrentPupilId()==item.pupil_id){
                itemView.rb_pupillist_item.isChecked = true
            }else{
                itemView.rb_pupillist_item.isChecked = false
            }
            itemView.rb_pupillist_item.setOnCheckedChangeListener { buttonView, isChecked ->
                Log.e(IMAKTAB, isChecked.toString()+item.pupil)
                App.setCurrentPupilId(item.pupil_id)

            }
        }
    }



}