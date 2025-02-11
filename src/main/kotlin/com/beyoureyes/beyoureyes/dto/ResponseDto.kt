package com.beyoureyes.beyoureyes.dto

import com.beyoureyes.beyoureyes.utils.ResponseStatus

data class ResponseDto<T>(
    val status: ResponseStatus,
    val message: String,
    val data: T?
)