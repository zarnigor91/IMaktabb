package com.example.imaktab.login

interface ILoginPresenter {
  fun login(loginRequest: LoginRequest)
  fun getParentId()
  fun getVerification()
  fun clearRequest()
}