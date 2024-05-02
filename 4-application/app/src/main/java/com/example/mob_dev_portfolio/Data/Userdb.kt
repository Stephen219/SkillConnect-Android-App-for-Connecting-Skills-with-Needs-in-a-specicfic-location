//package com.example.mob_dev_portfolio.Data
//
//import User
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//
//@Database(entities = [User::class], version = 1)
//
//abstract class Userdb : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: Userdb? = null
//
//        fun getDatabase(context: Context): Userdb {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    Userdb::class.java,
//                    "user_database"
//                ).build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
//}