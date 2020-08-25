package com.example.imaktab.network

import com.example.imaktab.about_app.AboutModel
import com.example.imaktab.settings.SettingsRequest
import com.example.imaktab.settings.SettingsResponSucces
import com.example.imaktab.classScheduleFragment.WeeklyLessonsModel
import com.example.imaktab.continuation_general.general.WeeklyContinuationModel
import com.example.imaktab.continuation_general.sciences.SciencesModel
import com.example.imaktab.continuation_general.sciences.Subjects
import com.example.imaktab.dashboard.continuation.SceduleModel
import com.example.imaktab.dashboard.PupilModel
import com.example.imaktab.dashboard.Total
import com.example.imaktab.login.Parent
import com.example.imaktab.login.LoginRequest
import com.example.imaktab.login.LoginResponce
import com.example.imaktab.dashboard.DashboardResponce
import com.example.imaktab.home_work.confirms.ConfirmRequest
import com.example.imaktab.home_work.confirms.ConfirmsResponce
import com.example.imaktab.home_work.passed.WeekLessonMarkModel
import com.example.imaktab.home_work.passed.WeekMarkModel
import com.example.imaktab.home_work.tomorrow_homeWork.OtherModel
import com.example.imaktab.home_work.tomorrow_homeWork.TommorowModel
import com.example.imaktab.ratings.by_science.MarkSciensModel
import com.example.imaktab.verification.CheckSmsRequestModel
import com.example.imaktab.verification.CheckSmsResponceModel
import com.example.imaktab.verification.VerificationModel
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

const val BASE_URL = "https://imaktab.wave.uz"
interface IApiClient {
     @POST("/rest-auth/login/")
     fun sigIn(@Body createLogin: LoginRequest): Single<LoginResponce>

     @GET("/api/v1/mobile/android/my_id")
     fun getIdParent(): Single<Parent>

     @GET("/api/v1/mobile/android/dashboard")
     fun getDashboardPupil(@Query("pupil")pupilid:Long): Single<DashboardResponce>

     @GET("/api/v1/mobile/android/total_gpa")
     fun getTotal(): Single<Total>

     @GET("/api/v1/mobile/android/schedule/today")
     fun getToday(@Query("pupil")pupilid:Long): Single<List<SceduleModel>>

     @GET("/api/v1/mobile/android/my_pupils")
     fun getPupilsByParentIdbyHeader(@Header("authorization") token:String, @Query("parent")parent_id :Long):Single<List<PupilModel>>

     @GET("/api/v1/mobile/android/my_pupils")
     fun getPupilListByParentId(@Query("parent")parent_id :Long):Single<List<PupilModel>>

     @GET("/api/v1/mobile/android/schedule/week")
     fun getweekScheduleByPupilId(@Query("pupil")pupilid:Long, @Query("data") date: String):Single<WeeklyLessonsModel>

     @GET("/api/v1/mobile/android/homeworks/tomorrow")
     fun getTomorrowByPupilId(@Query("pupil")pupilid:Long):Single<List<TommorowModel>>

     @GET("api/v1/mobile/android/homeworks/other")
     fun getOtherByPupilId(@Query("pupil")pupilid:Long):Single<List<OtherModel>>

     @POST("api/v1/mobile/android/homeworks/confirms")
     fun postConfirms(@Body confirmRequest: ConfirmRequest):Single<ConfirmsResponce>

     @GET(" api/v1/mobile/ios/homeworks/passed")
     fun getPassedByPupilId(@Query("pupil")pupilid:Long):Single<WeekLessonMarkModel>

     @GET("/api/v1/mobile/android/att")
     fun getWeekContinuation(@Query("pupil")pupilid:Long,@Query("date")date:String): Single<WeeklyContinuationModel>

     @GET("/api/v1/mobile/android/subjects")
     fun getSubjects(): Single<List<Subjects>>

     @GET("api/v1/mobile/ios/att/subject")
     fun getContinustionByMonth(@Query("pupil")pupilid:Int,@Query("subject")subject:Int,@Query("month")month:Int):Single<List<SciencesModel>>

     @GET("/api/v1/mobile/android/marks/week")
     fun getMarkByWeek(@Query("pupil")pupilid:Int,@Query("data")date:String):Single<WeekMarkModel>

     @GET("api/v1/mobile/android/marks/subject")
     fun getMarkByMonth(@Query("pupil")pupilid:Int,@Query("subject")subject:Int,@Query("month")month:Int):Single<List<MarkSciensModel>>

     @POST("/api/v1/mobile/change-password")
     fun changePassword(@Body changePassRequest: SettingsRequest): Single<Response<SettingsResponSucces>>

     @GET("api/v1/sms/verification")
     fun getVerification(@Query("number")number: String):Single<VerificationModel>

     @POST("api/v1/sms/verification")
     fun checkSms(@Body code:CheckSmsRequestModel):Single<CheckSmsResponceModel>

     @GET(" api/v1/mobile/about")
     fun getAboutApp():Single<AboutModel>
}