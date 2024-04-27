package com.example.emergency.respository.models

data class LoginResponseModel(
    val code: Int,
    val `data`: ResponseModelData,
    val status: Boolean
)

data class ResponseModelData(
    val message: String,
    val token: String,
    val user: ResponseModelUser
)

data class ResponseModelUser(
    val created_at: String,
    val custom_id: String,
    val description: String,
    val dob: String,
    val email: String,
    val experience: String,
    val fname: String,
    val gender: String,
    val id: Int,
    val latitude: String,
    val license_no: String,
    val lname: String,
    val longitude: String,
    val mobile_no: String,
    val phonecode: String,
    val profile_picture: String,
    val status: String,
    val token: String,
    val type: String,
    val updated_at: String,
    val upload_id: String,
    val zipcode: String
)