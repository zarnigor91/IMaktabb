package com.example.imaktab.home_work.confirms

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.imaktab.App
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.example.imaktab.home_work.HomeWorkFragment
import kotlinx.android.synthetic.main.check_work.*
import java.text.SimpleDateFormat
import java.util.*


class ConfirmsFragment :BaseFragment(R.layout.check_work),ConfirmsView{
    private val presenter:IConfirmsPeresenter=ConfirmsPresenterImple(this)
    var pupilId = App.getCurrentPupilId()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments
        val stringArray: Array<String> =
            arguments?.getStringArray("works") as Array<String>
        val parentId:String=stringArray[4]
        val workId:String=stringArray[3]
        tv_subject_check.text=stringArray[0]
        val month = arrayOf(
            "yanvar",
            "fevral",
            "mart",
            "aprel",
            "may",
            "iyun",
            "iyul",
            "avgustr",
            "sentabr",
            "oktabr",
            "noyabr",
            "dekabr"
        )
        val smdf = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = smdf.parse(stringArray[1])
        showProgressDialog()
        val day = DateFormat.format("dd", date) as String
        tv_day_work.text=day
        val months = (DateFormat.format("MM", date) as String).toInt()
        if (months > 0 && months < 13) {
            val uzmonth = month[months - 1]
//            val dates:String=day.toString()+uzmonth.toString()+year.toString()
           tv_month_work.text=uzmonth
        }
        val year = (DateFormat.format("yyyy", date) as String).toInt()
        tv_year_work.text=year.toString()
     tv_task.text=stringArray[2]
        im_back.setOnClickListener {
            fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,
                HomeWorkFragment()
            )?.commit()

        }
        tv_confirm.setOnClickListener {
            presenter.getConfirms(ConfirmRequest(pupilId!!.toInt(),parentId.toInt(),workId.toInt()))
        }
    }


    override fun onSuccess() {
        Toast.makeText(context,"Uyga vazifa yuklandi",Toast.LENGTH_SHORT).show()
    }

    override fun onError() {

    }
}