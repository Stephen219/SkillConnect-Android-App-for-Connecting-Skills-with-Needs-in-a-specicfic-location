package com.example.mob_dev_portfolio.Data

import Skill
import User
import UserSkillCrossRef
import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase


@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUser(userId: Long): User?
}


@Dao
interface SkillDao {
    @Insert
    suspend fun insert(skill: Skill)

    @Query("SELECT * FROM skills WHERE id = :skillId")
    suspend fun getSkill(skillId: Long): Skill?
}


@Dao
interface UserSkillCrossRefDao {
    @Insert
    fun insert(userSkillCrossRef: UserSkillCrossRef)

    @Query("SELECT * FROM user_skill_cross_ref WHERE userId = :userId")
    suspend fun getSkillsForUser(userId: Long): List<UserSkillCrossRef>
}


@Database(entities = [User::class, Skill::class, UserSkillCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun skillDao(): SkillDao
    abstract fun userSkillCrossRefDao(): UserSkillCrossRefDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
