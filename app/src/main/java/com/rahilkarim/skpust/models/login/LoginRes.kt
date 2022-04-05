package com.rahilkarim.skpust.models.login

data class LoginRes(
    val data: LoginData,
    val message: String,
    val status: Boolean
)