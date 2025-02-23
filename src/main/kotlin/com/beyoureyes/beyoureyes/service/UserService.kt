package com.beyoureyes.beyoureyes.service

import com.beyoureyes.beyoureyes.entity.User
import com.beyoureyes.beyoureyes.jwt.JwtUtil
import com.beyoureyes.beyoureyes.mapper.UserMapper
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService(private val userMapper: UserMapper, private val jwtUtil: JwtUtil) {

    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    fun deactivateInactiveUsers() {
        val affectedRows = userMapper.deactivateInactiveUsers()
        println("1년 이상 로그인하지 않은 사용자 $affectedRows 명 비활성화 되었습니다.")
    }

    fun login(deviceId: String): String {
        val user = userMapper.findByDeviceId(deviceId)

        // 비활성화된 계정이라면 로그인 불가
        if (user != null && user.deletedAt!= null) {
            throw IllegalStateException("해당 계정은 1년 이상 로그인하지 않아 비활성화되었습니다.")
        }

        val activeUser = user ?: run {
            val hashedDeviceId = BCrypt.hashpw(deviceId, BCrypt.gensalt())
            val newUser = User(deviceId = hashedDeviceId)
            userMapper.insertUser(newUser)
            newUser
        }

        userMapper.updateLastLogin(activeUser.userId!!)
        return jwtUtil.generateToken(activeUser.userId!!)
    }

    fun verifyToken(token : String): Boolean {
        return jwtUtil.validateToken(token)
    }

    fun getUserIdToken(token : String) : Long? {
        return jwtUtil.extractUserId(token)
    }
}