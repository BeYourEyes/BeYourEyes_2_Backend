package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {

    @Select("SELECT user_id, device_id FROM user")
    fun findAll() : List<User>

    @Select("SELECT user_id FROM user WHERE device_id = #{deviceId")
    fun findByDeviceId(deviceId : String) : User?

    @Insert("INSERT INTO user (device_id) VALUES (#{deviceId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    fun insertUser(user: User) : Int
}