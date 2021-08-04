package com.twentytwo.textme.RETROFIT

data class defaultresponse(val status:Boolean,val message: String)

data class LoginResponse(val status:Boolean,val message:String,val data:UserId)


data class UserId(val _id: String?, val name: String?, val locker: Int)


data class  BirthdayListResponse(
    val status: Boolean,
    val message: String,
    val result: ArrayList<result>
)

data class  result(
    var name: String?,
    var day:String?,
    var month:String?,
    var year: String?,
    var _id: String?,
    var reminder: Boolean?

)
//===============================================?