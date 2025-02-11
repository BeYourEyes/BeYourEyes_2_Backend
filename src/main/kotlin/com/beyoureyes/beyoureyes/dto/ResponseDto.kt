package com.beyoureyes.beyoureyes.dto

import com.beyoureyes.beyoureyes.utils.ResponseStatus
import lombok.Getter
import lombok.Setter

data class ResponseDto<T>(
    val status: ResponseStatus,
    val message: String,
    val data: T?
)