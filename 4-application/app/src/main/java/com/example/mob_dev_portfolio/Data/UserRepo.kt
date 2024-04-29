package com.example.mob_dev_portfolio.Data

import User

/**
 * Created by
 */
class UserRepo(private val userDao: UserDao) {
    val readAllData: List<User> = userDao.getUser()

    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    fun updateUser(user: User) {
        userDao.updateUser(user)
    }

}