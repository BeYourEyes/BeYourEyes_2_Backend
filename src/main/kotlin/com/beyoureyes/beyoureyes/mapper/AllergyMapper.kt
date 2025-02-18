package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.Allergy
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface AllergyMapper {
    @Insert("""
        INSERT INTO Allergy (user_id, buckwheat, wheat, soybean, peanut, walnut, pine_nut, sulfur_dioxide, 
                             peach, tomato, egg, milk, shrimp, mackerel, squid, crab, shellfish, pork, beef, chicken)
        VALUES (#{userId}, #{buckwheat}, #{wheat}, #{soybean}, #{peanut}, #{walnut}, #{pineNut}, #{sulfurDioxide}, 
                #{peach}, #{tomato}, #{egg}, #{milk}, #{shrimp}, #{mackerel}, #{squid}, #{crab}, #{shellfish}, #{pork}, #{beef}, #{chicken})
    """)
    fun insertAllergy(allergy: Allergy) : Int

    @Select("""
        SELECT * FROM Allergy WHERE user_id = #{UserId}
    """)
    fun getAllergyByUserId(userId : Long) : Allergy?
}