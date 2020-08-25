package com.example.imaktab.continuation_general.general

import org.threeten.bp.LocalDate

interface IGeneralPresenter {
  fun  getContinuationWeek(date:LocalDate)
}