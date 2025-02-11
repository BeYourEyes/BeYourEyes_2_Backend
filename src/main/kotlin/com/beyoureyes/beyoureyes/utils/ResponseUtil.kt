package com.beyoureyes.beyoureyes.utils

import com.beyoureyes.beyoureyes.dto.ResponseDto

object ResponseUtil {
    fun <T> success(message: String, data : T) : ResponseDto<T> {
        return ResponseDto(ResponseStatus.SUCCESS, message, data)
    }

    fun <T> failure(message : String, data : T) : ResponseDto<T> {
        return ResponseDto(ResponseStatus.FAILURE, message, data)
    }

    fun <T> error(message : String, data : T) : ResponseDto<T> {
        return ResponseDto(ResponseStatus.ERROR, message, data)
    }
}