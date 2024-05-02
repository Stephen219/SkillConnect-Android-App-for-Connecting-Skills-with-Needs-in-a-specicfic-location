//package com.example.mob_dev_portfolio.Data
//
//import User
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//
//class UserViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: UserRepo
//    val readAllData: List<User>
//
//    init {
//        val userDao = Userdb.getDatabase(application).userDao()
//        repository = UserRepo(userDao)
//        readAllData = repository.readAllData
//    }
//
//    fun addUser(user: User) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addUser(user)
//        }
//    }
//
//    fun deleteUser(user: User) {
//        repository.deleteUser(user)
//    }
//
//    fun updateUser(user: User) {
//        repository.updateUser(user)
//    }
//}