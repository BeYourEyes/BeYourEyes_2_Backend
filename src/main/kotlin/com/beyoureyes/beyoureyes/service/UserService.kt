package com.beyoureyes.beyoureyes.service

import com.beyoureyes.beyoureyes.entity.User
import com.beyoureyes.beyoureyes.jwt.JwtUtil
import com.beyoureyes.beyoureyes.mapper.UserMapper
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService(private val userMapper: UserMapper, private val jwtUtil: JwtUtil) {

    fun login(deviceId : String) : String {
        val users = userMapper.findAll()
        var existingUser: User? = null

        for (user in users) {
            if (BCrypt.checkpw(deviceId, user.deviceId)) {
                existingUser = user
                break
            }
        }

        val user = existingUser ?: run {
            val hashedDeviceId = BCrypt.hashpw(deviceId, BCrypt.gensalt())
            val newUser = User(deviceId = hashedDeviceId)
            userMapper.insertUser(newUser)
            newUser
        }

        if (existingUser != null) {
            userMapper.updateLastLogin(user.userId!!)
        }

        return jwtUtil.generateToken(user.userId!!)
    }

    fun verifyToken(token : String): Boolean {
        return jwtUtil.validateToken(token)
    }

    fun getUserIdToken(token : String) : Long? {
        return jwtUtil.extractUserId(token)
    }
}