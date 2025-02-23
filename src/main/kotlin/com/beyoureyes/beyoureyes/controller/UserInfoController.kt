package com.beyoureyes.beyoureyes.controller

import com.beyoureyes.beyoureyes.dto.ResponseDto
import com.beyoureyes.beyoureyes.entity.Allergy
import com.beyoureyes.beyoureyes.entity.Disease
import com.beyoureyes.beyoureyes.service.UserInfoService
import com.beyoureyes.beyoureyes.service.UserService
import com.beyoureyes.beyoureyes.utils.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

/* -
* 사용자 정보 저장 - 0 (세부 처리 필요) / 일 년 로그인안한 사람
* 사용자 정보 수정
* 해당 사용자 -> 오늘 섭취 저장 / firebase 연결 서버에서!복잡할까?0? - 한국 자정
* 해당 사용자 -> 오늘 섭취 저장 / 한국 자정
*
* 익명 로그인 - 0
* 안드로이드 로컬 저장소 토큰을 저장 - 0
* */
@RestController
@RequestMapping("/user")
class UserInfoController(private val userInfoService: UserInfoService) {

    @PostMapping("/save-user")
    fun saveUserInfo(@RequestBody request: Map<String, Any>): ResponseEntity<ResponseDto<Unit>> {
        // JWT 필터를 통해 인증된 사용자 ID 가져오기
        val userId = SecurityContextHolder.getContext().authentication.principal as Long

        val userBirth = request["user_birth"] as? String ?: return ResponseEntity.badRequest().body(ResponseUtil.error("생년월일이 필요합니다.", Unit))
        val userGender = request["user_gender"] as? Int ?: return ResponseEntity.badRequest().body(ResponseUtil.error("성별이 필요합니다.", Unit))
        val userNickname = request["user_nickname"] as? String ?: return ResponseEntity.badRequest().body(ResponseUtil.error("닉네임이 필요합니다.", Unit))

        // 알러지 정보 매핑
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

        // 질환 정보 매핑
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
    fun getUserInfo(): ResponseEntity<ResponseDto<Map<String, Any?>>> {
        // JWT 필터를 통해 인증된 사용자 ID 가져오기
        val userId = SecurityContextHolder.getContext().authentication.principal.toString().toLong()

        val (userInfo, allergy, disease) = userInfoService.getUserDetails(userId)

        if (userInfo == null) {
            return ResponseEntity.status(404).body(ResponseUtil.error("사용자 정보가 없습니다.", emptyMap()))
        }

        val responseData: Map<String, Any?> = mapOf(
            "userInfo" to userInfo,
            "allergy" to allergy,
            "disease" to disease
        )

        return ResponseEntity.ok(ResponseUtil.success("사용자 정보 조회 성공했습니다.", responseData))
    }

    @PatchMapping("/update")
    fun updateUserInfo(@RequestBody request: Map<String, Any>) :ResponseEntity<ResponseDto<Unit>> {
        val userId = SecurityContextHolder.getContext().authentication.principal as Long

        val userBirth = request["user_birth"] as? String
        val userGender = request["user_gender"] as? Int
        val userNickname = request["user_nickname"] as? String

        val allergyMap = request["allergy"] as? Map<String, Boolean>
        val diseaseMap = request["disease"] as? Map<String, Boolean>

        return if (userInfoService.updateUserInfo(userId, userBirth, userGender, userNickname, allergyMap, diseaseMap )) {
            ResponseEntity.ok(ResponseUtil.success("사용자 정보가 업데이트 되었습니다.", Unit))

        } else {
            ResponseEntity.status(500).body(ResponseUtil.error("사용자 정보 업데이트 실패했습니다.", Unit))
        }
    }
}