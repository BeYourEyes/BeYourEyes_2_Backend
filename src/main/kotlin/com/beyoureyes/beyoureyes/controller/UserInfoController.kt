package com.beyoureyes.beyoureyes.controller

import com.beyoureyes.beyoureyes.dto.ResponseDto
import com.beyoureyes.beyoureyes.entity.Allergy
import com.beyoureyes.beyoureyes.entity.Disease
import com.beyoureyes.beyoureyes.service.UserInfoService
import com.beyoureyes.beyoureyes.service.UserService
import com.beyoureyes.beyoureyes.utils.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserInfoController (private val userInfoService: UserInfoService, private val userService : UserService) {

    @PostMapping("/save-user")
    fun saveUserInfo(@RequestHeader("Authorization") authHeader : String?,
                     @RequestBody request : Map<String, Any>) : ResponseEntity<ResponseDto<Unit>> {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(ResponseUtil.error("토큰이 필요합니다.", Unit))
        }

        val token = authHeader.substring(7)
        val userId = userService.getUserIdToken(token) ?: return ResponseEntity.status(401).body(ResponseUtil.error("유효하지 않은 토큰입니다.", Unit))

        val userBirth = request["user_birth"] as? String ?: return ResponseEntity.badRequest().body(ResponseUtil.error("생년월일이 필요합니다.", Unit))
        val userGender = request["user_gender"] as? Int ?: return ResponseEntity.badRequest().body(ResponseUtil.error("성별이 필요합니다.", Unit))
        val userNickname = request["user_nickname"] as? String ?: return ResponseEntity.badRequest().body(ResponseUtil.error("닉네임이 필요합니다.", Unit))

        val allergyMap = request["allergy"] as? Map<String, Boolean> ?: return ResponseEntity.badRequest().body(ResponseUtil.error("알러지 정보가 필요합니다.", Unit))
        val allergy = Allergy(
            userId = userId,
            buckwheat = allergyMap["buckwheat"] ?: false,
            wheat = allergyMap["wheat"] ?: false,
            soybean = allergyMap["soybean"] ?: false,
            peanut = allergyMap["peanut"] ?: false,
            walnut = allergyMap["walnut"] ?: false,
            pineNut = allergyMap["pineNut"] ?: false,
            sulfurDioxide = allergyMap["sulfurDioxide"] ?: false,
            peach = allergyMap["peach"] ?: false,
            tomato = allergyMap["tomato"] ?: false,
            egg = allergyMap["egg"] ?: false,
            milk = allergyMap["milk"] ?: false,
            shrimp = allergyMap["shrimp"] ?: false,
            mackerel = allergyMap["mackerel"] ?: false,
            squid = allergyMap["squid"] ?: false,
            crab = allergyMap["crab"] ?: false,
            shellfish = allergyMap["shellfish"] ?: false,
            pork = allergyMap["pork"] ?: false,
            beef = allergyMap["beef"] ?: false,
            chicken = allergyMap["chicken"] ?: false
        )

        val diseaseMap = request["disease"] as? Map<String, Boolean> ?: return ResponseEntity.badRequest().body(ResponseUtil.error("질환 정보가 필요합니다.", Unit))
        val disease = Disease(
            userId = userId,
            diabetes = diseaseMap["diabetes"] ?: false,
            hypertension = diseaseMap["hypertension"] ?: false,
            hyperlipidemia = diseaseMap["hyperlipidemia"] ?: false
        )

        return if (userInfoService.saveUserInfo(userId, userBirth, userGender, userNickname, allergy, disease)) {
            ResponseEntity.ok(ResponseUtil.success("사용자 정보가 저장되었습니다.", Unit))
        } else {
            ResponseEntity.status(500).body(ResponseUtil.error("사용자 정보 저장 실패", Unit))
        }
    }

    @GetMapping("/user-info")
    fun getUserInfo(@RequestHeader("Authorization") authHeader: String?) : ResponseEntity<ResponseDto<Map<String, Any?>>>{
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body(ResponseUtil.error("토큰이 필요합니다.", emptyMap()))
        }

        val token = authHeader.substring(7)
        val userId = userService.getUserIdToken(token) ?: return ResponseEntity.status(401).body(ResponseUtil.error("유효하지 않은 토큰입니다.", emptyMap()))

        val (userInfo, allergy, disease) = userInfoService.getUserDetails(userId)

        if (userInfo == null) {
            return ResponseEntity.status(404).body(ResponseUtil.error("사용자 정보가 없습니다.", emptyMap()))
        }

        val responseData : Map<String, Any?> = mapOf(
            "userInfo" to userInfo,
            "allergy" to allergy,
            "disease" to disease
        )

        return ResponseEntity.ok(ResponseUtil.success("사용자 정보 조회 성공했습니다.", responseData))
    }
}