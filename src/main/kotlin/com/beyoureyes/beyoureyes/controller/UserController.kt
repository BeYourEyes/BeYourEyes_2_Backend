package com.beyoureyes.beyoureyes.controller

import com.beyoureyes.beyoureyes.service.Userservice
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userservice: Userservice) {

    @PostMapping("/login")
    fun postLogin(@RequestBody request : Map<String, String>) : ResponseEntity<Map<String, Any>> {
        val deviceId = request["device_id"] ?: return ResponseEntity.badRequest().body(mapOf("message" to "device_id가 필요합니다."))
        val token = userservice.login(deviceId)
        return ResponseEntity.ok(mapOf("message" to "익명 로그인 처리 되었습니다.", "token" to token))
    }

    @PostMapping("/verify-token")
    fun postVerify(@RequestBody request: Map<String, String>): ResponseEntity<Map<String, String>> {
        val token = request["token"] ?: return ResponseEntity.badRequest().body(mapOf("message" to "token이 필요합니다."))

        return if (userservice.verifyToken(token)) {
            ResponseEntity.ok(mapOf("message" to "유효한 토큰입니다."))
        } else {
            ResponseEntity.status(401).body(mapOf("message" to "토큰이 유효하지 않습니다."))
        }
    }
}