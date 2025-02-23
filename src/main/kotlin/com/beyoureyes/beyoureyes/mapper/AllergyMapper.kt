package com.beyoureyes.beyoureyes.mapper

import com.beyoureyes.beyoureyes.entity.Allergy
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.apache.ibatis.annotations.Update

@Mapper
interface AllergyMapper {
    @Update("""
        CREATE TABLE IF NOT EXISTS Allergy (
            allergy_id INT AUTO_INCREMENT PRIMARY KEY,
            user_id INT NOT NULL,
            buckwheat BOOLEAN DEFAULT FALSE,
            wheat BOOLEAN DEFAULT FALSE,
            soybean BOOLEAN DEFAULT FALSE,
            peanut BOOLEAN DEFAULT FALSE,
            walnut BOOLEAN DEFAULT FALSE,
            pine_nut BOOLEAN DEFAULT FALSE,
            sulfur_dioxide BOOLEAN DEFAULT FALSE,
            peach BOOLEAN DEFAULT FALSE,
            tomato BOOLEAN DEFAULT FALSE,
            egg BOOLEAN DEFAULT FALSE,
            milk BOOLEAN DEFAULT FALSE,
            shrimp BOOLEAN DEFAULT FALSE,
            mackerel BOOLEAN DEFAULT FALSE,
            squid BOOLEAN DEFAULT FALSE,
            crab BOOLEAN DEFAULT FALSE,
            shellfish BOOLEAN DEFAULT FALSE,
            pork BOOLEAN DEFAULT FALSE,
            beef BOOLEAN DEFAULT FALSE,
            chicken BOOLEAN DEFAULT FALSE,
            CONSTRAINT fk_allergy_user_id FOREIGN KEY (user_id) REFERENCES userInfo(user_id) ON DELETE CASCADE
        )
    """)
    fun createTableIfNotExists()

    @Insert("""
        INSERT INTO Allergy (user_id, buckwheat, wheat, soybean, peanut, walnut, pine_nut, sulfur_dioxide, 
                             peach, tomato, egg, milk, shrimp, mackerel, squid, crab, shellfish, pork, beef, chicken)
        VALUES (#{userId}, #{buckwheat}, #{wheat}, #{soybean}, #{peanut}, #{walnut}, #{pineNut}, #{sulfurDioxide}, 
                #{peach}, #{tomato}, #{egg}, #{milk}, #{shrimp}, #{mackerel}, #{squid}, #{crab}, #{shellfish}, #{pork}, #{beef}, #{chicken})
    """)
    fun insertAllergy(allergy: Allergy) : Int

    @Select("""
        SELECT * FROM Allergy WHERE user_id = #{userId}
    """)
    fun getAllergyByUserId(userId : Long) : Allergy?
}