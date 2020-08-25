package com.example.imaktab.home_work.tomorrow_homeWork

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imaktab.App
import com.example.imaktab.BaseFragment
import com.example.imaktab.PARENT_ID_KEY
import com.example.imaktab.R
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.home_work.confirms.ConfirmsFragment
import com.example.imaktab.home_work.HomeWorkPresenterImple
import com.example.imaktab.home_work.HomeWorkView
import kotlinx.android.synthetic.main.home_work_layout.*
import kotlinx.android.synthetic.main.lesson_fragment.*
import kotlinx.android.synthetic.main.tomorrows_tasks_fragment.*

class TomorrowFragment :BaseFragment(R.layout.tomorrows_tasks_fragment),
    HomeWorkView {
    private var adapter: TomorrowAdapter? = null
    private var adapterOther: OtherAdapter? = null
    private val presenter: HomeWorkPresenterImple =
        HomeWorkPresenterImple(this)

    override fun onStart() {
        super.onStart()
        presenter.tomorrow()
        presenter.other()

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      initRv()
    }
      private fun initRv(){

          adapter= TomorrowAdapter(listOf(),this::subjectAction)
          rv_tomorrow.layoutManager = LinearLayoutManager(this@TomorrowFragment.context)
          rv_tomorrow.adapter=adapter

          adapterOther=OtherAdapter(listOf(),this::subjectAction1)
          rv_tomorrow.layoutManager = LinearLayoutManager(this@TomorrowFragment.context)
          rv_rest.adapter=adapterOther
      }
    private fun getPupils() {
        App.getPupilList()?.let { onGetPupilSuccess(it) }
        //presenter.getPupilListByParentId()
    }

    fun getList():List<Subject>{
        val getlist= listOf<Subject>(Subject("MAtematika"),Subject("Informatika"),Subject("Fizika"))
        return getlist
    }

    fun subjectAction(subject: TommorowModel){
        val works = arrayOf(subject.subject, subject.date, subject.task, subject.id.toString(),parentId().toString())
        val fragment: Fragment =
            ConfirmsFragment()
        val args = Bundle()
        args.putStringArray("works",works)
        fragment.arguments = args
     fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,fragment)?.commit()
    }

    fun subjectAction1(subject: OtherModel){

        val fragment: Fragment =
            ConfirmsFragment()
        val args = Bundle()

        fragment.arguments = args
        fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.container,fragment)?.commit()
    }

    override fun getTomorrow(tommorowModel: List<TommorowModel>){
      adapter!!.updateList(tommorowModel,this::subjectAction)
        Log.d("tom","fdd"+tommorowModel)
    }

    override fun getOther(otherModel: List<OtherModel>) {
        adapterOther!!.updateList1(otherModel,this::subjectAction1)
    }

    override fun parentId(): Long {
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val parentId = sharedPreferences.getLong(PARENT_ID_KEY, 0)
        return parentId
        return 1
    }

    override fun onGetPupilSuccess(pupilList: List<PupilModel>) {
        if (pupilList != null) {
            App.setPupilList(pupilList as MutableList)
            var currentPupilId = App.getCurrentPupilId()
            if (currentPupilId == null) {
                tv_pupil_work.text = pupilList[0].pupil
                tv_class_work.text = pupilList[0].klass
                App.setCurrentPupilId(pupilList[0].pupil_id)
            } else {
                var flag = true
                for (i in 0..pupilList.size) {
                    if (currentPupilId == pupilList[i].pupil_id) {
                        tv_user.text = pupilList[i].pupil
                        tv_class_lessonfr.text = pupilList[i].klass
                        flag = false
                        break
                    }
                }
                if (flag) {
                    tv_user.text = pupilList[0].pupil
                    tv_class_lessonfr.text = pupilList[0].klass
                    App.setCurrentPupilId(pupilList[0].pupil_id)
                }
            }

        }
        presenter.tomorrow()
        presenter.other()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearRequest()
    }


}
