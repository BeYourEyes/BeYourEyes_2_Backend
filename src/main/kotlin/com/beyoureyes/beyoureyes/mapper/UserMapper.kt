package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.User
import org.apache.ibatis.annotations.*

@Mapper
interface UserMapper {

    @Update("""
        CREATE TABLE IF NOT EXISTS user (
            user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
            device_id VARCHAR(255) UNIQUE NOT NULL,
            last_login TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            deleted_at TIMESTAMP NULL DEFAULT NULL
        )
    """)
    fun createTableNotExists()

    @Select("SELECT user_id, device_id FROM user")
    fun findAll() : List<User>

    @Select("SELECT user_id FROM user WHERE device_id = #{deviceId}")
    fun findByDeviceId(deviceId : String) : User?

    @Insert("INSERT INTO user (device_id) VALUES (#{deviceId})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    fun insertUser(user: User) : Int

    @Update("UPDATE user SET last_login = NOW() WHERE user_id = #{userId}")
    fun updateLastLogin(userId : Long)

    @Update("""
        UPDATE user
        SET deleted_at = NOW()
        WHERE last_login < NOW() - INTERVAL 1 YEAR AND deleted_at IS NULL
    """)
    fun deactivateInactiveUsers(): Int

    @Select("SELECT * FROM user WHERE device_id = #{deviceId} AND deleted_at IS NULL")
    fun findActiveUserByDeviceId(deviceId: String) : User?

    @Update("UPDATE user SET deleted_at = NULL WHERE user_id = #{userId}")
    fun reactivateUser(userId: Long) : Int
}