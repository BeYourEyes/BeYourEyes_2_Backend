package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.UserInfo
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Options
import org.apache.ibatis.annotations.Select

@Mapper
interface UserInfoMapper {
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