package com.example.mob_dev_portfolio.Data


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

/**
 * this is the entity class for the user table and any other table that will be created
 * it is a data class that holds the user data
 *
 */
@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "website")
    val website: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "skill")
    val skill: String,
    @ColumnInfo(name = "location")
    val location: String?
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "")

}
/**
 * this is the entity class for the skill table and any other table that will be created
 * it is a data class that holds the skill data   to be used
 *
 */

@Entity(tableName = "skills")
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val skillName: String
)

/**
 * this is the entity class for the user_skill_cross_ref table and any other table that will be created
 * ** to be used
 */

@Entity(
    tableName = "user_skill_cross_ref",
    primaryKeys = ["userId", "skillId"],
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = Skill::class, parentColumns = ["id"], childColumns = ["skillId"])
    ]
)
data class UserSkillCrossRef(
    val userId: Long,
    val skillId: Long
)
