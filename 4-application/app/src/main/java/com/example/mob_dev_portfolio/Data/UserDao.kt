//package com.example.mob_dev_portfolio.Data
//
//import User
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//
//interface UserDao {
//    @Insert (onConflict = OnConflictStrategy.REPLACE)
//    suspend fun addUser(user: User)
//
//    @Query("SELECT * FROM users ORDER BY id ASC")
//    fun getUser(): List<User> // get all users but the tutorial says live data   todo: check on tyhat
//    fun deleteUser(user: User)
//    fun updateUser(user: User)
//}