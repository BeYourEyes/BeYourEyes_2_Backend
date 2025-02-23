package com.beyoureyes.beyoureyes.controller

import com.beyoureyes.beyoureyes.dto.ResponseDto
import com.beyoureyes.beyoureyes.service.UserService
import com.beyoureyes.beyoureyes.utils.ResponseUtil
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/login")
    fun postLogin(@RequestBody request: Map<String, String>): ResponseEntity<ResponseDto<String>> {
        val deviceId = request["device_id"]
            ?: return ResponseEntity.badRequest().body(ResponseUtil.error("device_id가 필요합니다.", ""))

        val token = userService.login(deviceId)
        return ResponseEntity.ok(ResponseUtil.success("익명 로그인 처리 되었습니다.", token))
    }


    @PostMapping("/verify-token")
    fun postVerify(@RequestBody request: Map<String, String>): ResponseEntity<ResponseDto<String>> {
        val token = request["token"] ?:
        return ResponseEntity.badRequest().body(ResponseUtil.error("token이 필요합니다.", ""))

        return if (userService.verifyToken(token)) {
            ResponseEntity.ok(ResponseUtil.success("유효한 토큰입니다.", token))
        } else {
            ResponseEntity.badRequest().body(ResponseUtil.error("토큰이 유효하지 않습니다.", ""))
        }
    }
}