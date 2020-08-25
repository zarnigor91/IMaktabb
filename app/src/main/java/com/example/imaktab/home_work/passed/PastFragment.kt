package com.example.imaktab.home_work.passed

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import kotlinx.android.synthetic.main.past_tasks_fragment.*

class PastFragment : BaseFragment(R.layout.past_tasks_fragment), PassedView {
    private var adapterWeek: WeekLessonsMarkAdapter? = null
    private val presenter: IPassedPresenter = PassedPresenterImple(this)
    var emptyList = ArrayList<DailyLessonsMarkModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        adapterWeek= WeekLessonsMarkAdapter(context!!, emptyList)
        rv_pas_lesson.layoutManager = LinearLayoutManager(context)
        rv_pas_lesson.adapter = adapterWeek
        showProgressDialog()
    }

    override fun onStart() {
        super.onStart()
        presenter.getPassed()
    }

    override fun onResume() {
        super.onResume()
        presenter.getPassed()
    }

    private fun convertWeeklyToDaily(weekLessonMarkModel: WeekLessonMarkModel): List<DailyLessonsMarkModel> {
        var list = mutableListOf<DailyLessonsMarkModel>()

        list.add(DailyLessonsMarkModel(getString(R.string.monday), "", weekLessonMarkModel.monday))
        list.add(DailyLessonsMarkModel(getString(R.string.tuesday), "", weekLessonMarkModel.tuesday))
        list.add(DailyLessonsMarkModel(getString(R.string.wednesday), "", weekLessonMarkModel.wednesday))
        list.add(DailyLessonsMarkModel(getString(R.string.thursday), "", weekLessonMarkModel.thursday))
        list.add(DailyLessonsMarkModel(getString(R.string.friday), "", weekLessonMarkModel.friday))
        list.add(DailyLessonsMarkModel(getString(R.string.saturday), "", weekLessonMarkModel.saturday))
        return list
    }

    override fun getPassedvalue(weekLessonMarkModel:WeekLessonMarkModel) {
        emptyList = convertWeeklyToDaily(weekLessonMarkModel) as ArrayList<DailyLessonsMarkModel>
        Log.d("TTT", "passed" + emptyList)
        hideProgressDialog()
        adapterWeek?.updateList(emptyList)
    }

    override fun onSucces() {

    }

    override fun onError() {

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.clearRequest()
    }

}