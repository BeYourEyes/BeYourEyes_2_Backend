package com.beyoureyes.beyoureyes.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class LoginRequestDto(
    @Schema(description = "디바이스 ID", example = "your-device-id")
    @field:NotBlank(message = "device_id는 필수 입력값입니다.") // 공백, 널 값 방지
    val device_id: String
)