//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.room.Room
//import android.content.Context
//import android.widget.Toast
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//
//@Database(entities = [User::class], version = 1)
//abstract class UserDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: UserDatabase? = null
//
//        fun getDatabase(context: Context): UserDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    UserDatabase::class.java,
//                    "user_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
//
//
//
//
//
//@Dao
//interface UserDao {
//    @Insert
//    suspend fun insertUser(user: User)
//
//    @Update
//    suspend fun updateUser(user: User)
//
//    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
//    suspend fun getUserById(userId: String): User?
//
//
//
//
//
//    fun insertUser(context: Context, user: User) {
//        val database = UserDatabase.getDatabase(context)
//        val userDao = database.userDao()
//
//
//        CoroutineScope(Dispatchers.IO).launch {
//            userDao.insertUser(user)
//        }
//    }
//
//
//    fun getUserById(context: Context, userId: String): User? {
//        val database = UserDatabase.getDatabase(context)
//        val userDao = database.userDao()
//
//        return runBlocking {
//            val user = userDao.getUserById(userId)
//
//            if (user == null) {
//                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
//            }
//
//            user
//        }
//    }
//}
