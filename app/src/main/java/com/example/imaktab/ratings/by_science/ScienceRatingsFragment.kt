package com.example.imaktab.ratings.by_science

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bigkoo.pickerview.MyOptionsPickerView
import com.example.imaktab.BaseFragment
import com.example.imaktab.R
import com.example.imaktab.continuation_general.sciences.*
import kotlinx.android.synthetic.main.marks_on_the_subjects_fragment.*
import kotlinx.android.synthetic.main.sciencess_fragment.*
import org.threeten.bp.LocalDate
import org.threeten.bp.Month
import java.util.*
import kotlin.collections.ArrayList

class ScienceRatingsFragment:BaseFragment(R.layout.marks_on_the_subjects_fragment),IScienMarkView{
    private var adapterSciMark: ScienMarkAdapter?=null
    private val presenterScienMark: IScienMarkPresenter = ScienMarkPresenterImple(this)
    private var list: Subjects?=null
    private var selectedSubject: Int? = null
    private var selectedMonth: Int = 0
    private var selectedSubjectIndex: Int = 0
    private val monthList = ArrayList<String>()
    private var subjectList = mutableListOf<Subjects>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         init()

        presenterScienMark.getSubjects()
        tv_month_ratings.text = monthList[LocalDate.now().month.value-1]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedMonth= LocalDate.now().month.value -1
        presenterScienMark.getMarkBySubjectAndMonth(1, selectedMonth)
    }

    fun init(){
        adapterSciMark= ScienMarkAdapter(listOf())
        rv_mark_scien.layoutManager= LinearLayoutManager(context)
        rv_mark_scien.adapter=adapterSciMark
        tv_subject_ratings.setOnClickListener {
            picerSubject()
            Toast.makeText(context,"fan",Toast.LENGTH_SHORT).show()
        }
        monthList.addAll(arrayListOf(
            resources.getString(R.string.januar),
            resources.getString(R.string.februare),
            resources.getString(R.string.march),
            resources.getString(R.string.april),
            resources.getString(R.string.may),
            resources.getString(R.string.june),
            resources.getString(R.string.july),
            resources.getString(R.string.august),
            resources.getString(R.string.september),
            resources.getString(R.string.october),
            resources.getString(R.string.november),
            resources.getString(R.string.december)
        ))
        tv_month_ratings.setOnClickListener {
            picerMonth()
        }
    }

    override fun getSubjectScien(subjects: Subjects) {
        list=subjects
    }

    override fun onGetAttendanceBySubjectAndMonth(markSciensModel: List<MarkSciensModel>) {
        adapterSciMark!!.updateList(markSciensModel)
    }

    override fun onGetSubjects(subjects: List<Subjects>) {
        subjectList.clear()
        subjectList.addAll(subjects)
        if (subjectList.isNotEmpty()) {
            selectedSubject = 0
            tv_subject_ratings.text = subjectList[0].name
            tv_subject_by_rating.text=subjectList[0].name
            getRatingBySubjectAndMonth()
        }
    }


    private fun picerSubject(){
        val singlePicker: MyOptionsPickerView<String> = MyOptionsPickerView(context)
        if (subjectList.size != 0) {
            val items = ArrayList<String>()
            for (i in subjectList) {
                i.name?.let { items.add(it) }
            }
            singlePicker.setPicker(items)
            singlePicker.setTitle("Choose science")
            singlePicker.setCyclic(false)
            singlePicker.setSelectOptions(selectedSubjectIndex)

            singlePicker.setOnoptionsSelectListener { p1, p2, p3 ->
                selectedSubjectIndex = p1
                tv_subject_ratings.text = items[p1]
                tv_subject_by_rating.text=items[p1]
                getRatingBySubjectAndMonth()
            }
            singlePicker.show()
        }
    }
    private fun picerMonth(){
        val singlePicker: MyOptionsPickerView<String> = MyOptionsPickerView(context)

        singlePicker.setPicker(monthList)
        singlePicker.setTitle("Choose month")
        singlePicker.setCyclic(false)
        singlePicker.setSelectOptions(selectedMonth)

        singlePicker.setOnoptionsSelectListener { p1, p2, p3 ->
            selectedMonth = p1
            tv_month_ratings.text = monthList[p1]

            getRatingBySubjectAndMonth()
        }
        singlePicker.show()
    }

    private fun getRatingBySubjectAndMonth() {
        presenterScienMark.getMarkBySubjectAndMonth(subjectList[selectedSubjectIndex].id!!, selectedMonth+1)
    }
}