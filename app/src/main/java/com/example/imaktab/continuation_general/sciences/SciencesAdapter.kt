package com.example.imaktab.continuation_general.sciences

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imaktab.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_week_of_day_conti.view.*
import java.text.SimpleDateFormat
import java.util.*


class SciencesAdapter(val list:List<SciencesModel>):RecyclerView.Adapter<SciencesAdapter.VH>(){
    private var myListSubject:MutableList<SciencesModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.item_week_of_day_conti,parent,false)
          return VH(view)
    }

    override fun getItemCount(): Int=myListSubject.size

    override fun onBindViewHolder(holder: VH, position: Int) {
       val item =myListSubject[position]
        holder.onBind(item)
    }
    inner class VH(override val containerView: View?):
        RecyclerView.ViewHolder(containerView!!),LayoutContainer{
        fun onBind(item: SciencesModel) {
            if (item.date==null)
            {

                itemView.tv_sci_mont.text=""
            }
            else {


                if (item.date.isNotEmpty()) {
                    val smdf = SimpleDateFormat("yyyy-MM-dd")
                    val date: Date = smdf.parse(item.date)
                    val day = DateFormat.format("dd", date) as String
                    when (day) {
                        "01" -> itemView.tv_sci_mont.text = "1"
                        "02" -> itemView.tv_sci_mont.text = "2"
                        "03" -> itemView.tv_sci_mont.text = "3"
                        "04" -> itemView.tv_sci_mont.text = "4"
                        "05" -> itemView.tv_sci_mont.text = "5"
                        "06" -> itemView.tv_sci_mont.text = "6"
                        "07" -> itemView.tv_sci_mont.text = "7"
                        "08" -> itemView.tv_sci_mont.text = "8"
                        "09" -> itemView.tv_sci_mont.text = "9"
                        else -> itemView.tv_sci_mont.text = day
                    }
                    val months = (DateFormat.format("MM", date) as String).toInt()
                    val cal = Calendar.getInstance()
                    cal.time = date
                     when(cal.get(Calendar.DAY_OF_WEEK_IN_MONTH)){
                         1->itemView.tv_wekOfDay_scie.setText(R.string.monday)
                         2->itemView.tv_wekOfDay_scie.setText(R.string.tuesday)
                         3->itemView.tv_wekOfDay_scie.setText(R.string.wednesday)
                         4->itemView.tv_wekOfDay_scie.setText(R.string.thursday)
                         5->itemView.tv_wekOfDay_scie.setText(R.string.friday)
                         6->itemView.tv_wekOfDay_scie.setText(R.string.saturday)
                     }

                }
            }
            itemView.tv_room_scie.text=item.room
            if (item.att==true)
            {  itemView.tv_came_scie.text="Kelgan"
                itemView.im_check_scien.setImageResource(R.drawable.ic_ellepis_green)

            }
            else
            {
                itemView.tv_came_scie.text="Kelmagan"}
            itemView.im_check_scien.setImageResource(R.drawable.ic_ellepis_red)
        }

    }
    fun updateList(newlist: List<SciencesModel>){
        myListSubject.clear()
        myListSubject.addAll(newlist)
        this.notifyDataSetChanged()
    }


}

