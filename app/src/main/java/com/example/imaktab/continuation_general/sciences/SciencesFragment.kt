package com.example.imaktab.continuation_general.sciences

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bigkoo.pickerview.MyOptionsPickerView
import com.example.imaktab.BaseFragment
import com.example.imaktab.IMAKTAB
import com.example.imaktab.R
import kotlinx.android.synthetic.main.sciencess_fragment.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList

class SciencesFragment : BaseFragment(R.layout.sciencess_fragment), SciencesView {
    private var monthList = arrayListOf<String>()

    private var adapterScien: SciencesAdapter? = null
    private val presenter: ISciencesPresenter = SciencesPresenterImple(this)
    private var list: Subjects? = null
    private var selectedSubject: Int? = null
    private var selectedMonth: Int = 0
    private var selectedSubjectIndex: Int = 0
    private var subjectList = mutableListOf<Subjects>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        monthList = arrayListOf(
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
        )
//        tv_subject_scien.setOnClickListener {
//           picerSubject()
//        }
        init()

        presenter.getSubjects()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        presenter.getScience(1,3)
    }

    fun init() {
        showProgressDialog()
        tv_month.text = getResources().getString(R.string.januar)
        adapterScien = SciencesAdapter(listOf())
        rv_sciencess.layoutManager = GridLayoutManager(context, 2)
        rv_sciencess.adapter = adapterScien

        tv_subject_scien.setOnClickListener {
            picerSubject()
        }
        tv_month.setOnClickListener {
            picerMonth()
        }
    }

    override fun onGetSubjects(inputSubjectLIst: List<Subjects>) {
        subjectList.clear()
        subjectList.addAll(inputSubjectLIst)
        if (subjectList.isNotEmpty()) {
            selectedSubject = 0
            tv_subject_scien.text = subjectList[0].name
            val month = Calendar.getInstance()[Calendar.MONTH]
            presenter.getAttendanceBySubjectAndMonth(
                1,
                month
            )
            selectedMonth = month
            tv_month.text = monthList[month]
            tv_momonth.text = monthList[month]
        }
    }

    fun picerSubject() {
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
                tv_subject_scien.text = items[p1]
                getAttendanceBySubjectAndMonth()
            }
            singlePicker.show()
        }
    }

    fun picerMonth() {
        val singlePicker: MyOptionsPickerView<String> = MyOptionsPickerView(context)

        singlePicker.setPicker(monthList)
        singlePicker.setTitle("Choose month")
        singlePicker.setCyclic(false)
        singlePicker.setSelectOptions(selectedMonth)

        singlePicker.setOnoptionsSelectListener { p1, p2, p3 ->
            selectedMonth = p1
            tv_month.text = monthList[p1]
            tv_momonth.text = monthList[p1]
            getAttendanceBySubjectAndMonth()
        }
        singlePicker.show()
    }

    override fun onGetAttendanceBySubjectAndMonth(sciencesModel: List<SciencesModel>) {
        Log.d(IMAKTAB, "AttLessonsApadter updated: " + sciencesModel)
        hideProgressDialog()
        adapterScien!!.updateList(sciencesModel)
    }

    private fun getAttendanceBySubjectAndMonth() {
        //TODO("set month")
        val month = Calendar.getInstance()[Calendar.MONTH]
        presenter.getAttendanceBySubjectAndMonth(
            subjectList[selectedSubjectIndex].id!!,
            selectedMonth + 1
        )
    }
}