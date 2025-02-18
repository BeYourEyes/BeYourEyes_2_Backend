package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.UserInfo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface UserInfoMapper {

    @Update("""
        CREATE TABLE IF NOT EXISTS userInfo (
            user_info_id INT AUTO_INCREMENT PRIMARY KEY,
            user_id INT NOT NULL,
            user_birth TIMESTAMP NOT NULL,
            user_gender TINYINT NOT NULL,
            user_nickname VARCHAR(255) NOT NULL,
            CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
        )
    """)
    fun createTableIfNotExists()

    @Insert("""
        INSERT INTO userInfo (user_id, user_birth, user_gender, user_nickname)
        VALUES (#{userId}, #{userBirth}, #{userGender}, #{userNickname})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "userInfoId")
    fun insertUserInfo(userInfo : UserInfo) : Int

    @Select("""
        SELECT * FROM userInfo WHERE user_id = #{userId}
    """)
    fun getUserInfoByUserId(userId: Long) : UserInfo?

}