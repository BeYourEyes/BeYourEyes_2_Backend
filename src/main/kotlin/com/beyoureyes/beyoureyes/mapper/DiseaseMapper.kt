package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.Disease
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface DiseaseMapper {
    @Insert(
        """
        INSERT INTO Disease (user_id, diabetes, hypertension, hyperlipidemia)
        VALUES (#{userId}, #{diabetes}, #{hypertension}, #{hyperlipidemia})
    """
    )
    fun insertDisease(disease: Disease): Int

    @Select(
        """
        SELECT * FROM Disease WHERE user_id = #{userId}
    """
    )
    fun getDiseaseByUserId(userId: Long): Disease?
}