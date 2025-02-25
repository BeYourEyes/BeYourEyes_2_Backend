package com.beyoureyes.beyoureyes.controller

import com.beyoureyes.beyoureyes.dto.LoginRequestDto
import com.beyoureyes.beyoureyes.dto.ResponseDto
import com.beyoureyes.beyoureyes.service.UserService
import com.beyoureyes.beyoureyes.utils.ResponseUtil
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
@Tag(name = "User API", description = "사용자 정보 API")
class UserController(private val userService: UserService) {


    @PostMapping("/login")
    @Operation(
        summary = "익명 로그인",
        description = "안드로이드 디바이스 id를 이용해 토큰을 발급합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "로그인 성공",
                content = [Content(
                    schema = Schema(implementation = ResponseDto::class),
                    examples = [ExampleObject(value = """
                        {
                          "status": "SUCCESS",
                          "message": "익명 로그인 처리 되었습니다.",
                          "data": "발급된 JWT 토큰 값"
                        }
                    """)]
                )]
            ),
            ApiResponse(
                responseCode = "400",
                description = "디바이스 ID 빈 값",
                content = [Content(
                    examples = [ExampleObject(value = """
                        {
                          "status" : "ERROR",
                          "message" : "device_id가 빈값입니다.",
                          "data" : ""
                        }
                    """)]
                )]
            ),
            ApiResponse(
                responseCode = "500",
                description = "서버 오류",
                content = [Content(
                    examples = [ExampleObject(value = """
                        {
                          "status": "ERROR",
                          "message": "서버 오류가 발생했습니다.",
                          "data": ""
                        }
                    """)]
                )]
            )
        ]
    )
    fun postLogin(@Valid @RequestBody request: LoginRequestDto): ResponseEntity<ResponseDto<String>> {
        val deviceId = request.device_id
        if (deviceId == "") {
            return ResponseEntity.badRequest().body(ResponseUtil.error("device_id가 빈값입니다.", ""))
        }
        val token = userService.login(deviceId)
        return ResponseEntity.ok(ResponseUtil.success("익명 로그인 처리 되었습니다.", token))
    }




    @PostMapping("/verify-token")
    fun postVerify(@RequestBody request: Map<String, String>): ResponseEntity<ResponseDto<String>> {
        val token = request["token"]
            ?: return ResponseEntity.badRequest().body(ResponseUtil.error("token이 필요합니다.", ""))

        return if (userService.verifyToken(token)) {
            ResponseEntity.ok(ResponseUtil.success("유효한 토큰입니다.", token))
        } else {
            ResponseEntity.badRequest().body(ResponseUtil.error("토큰이 유효하지 않습니다.", ""))
        }
    }
}