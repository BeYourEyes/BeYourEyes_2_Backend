package com.beyoureyes.beyoureyes.entity

import java.time.LocalDateTime

data class UserInfo (
    val userInfoId : Long? = null,
    val userId : Long,
    val userBirth : LocalDateTime,
    val userGender : Int,
    val userNickname : String
)