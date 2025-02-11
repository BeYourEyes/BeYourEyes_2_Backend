package com.beyoureyes.beyoureyes.service

import com.beyoureyes.beyoureyes.mapper.UserMapper
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service

@Service
class DatabaseService(private val userMapper : UserMapper) {
    @PostConstruct
    fun init() {
        userMapper.createTableNotExists()
    }
}