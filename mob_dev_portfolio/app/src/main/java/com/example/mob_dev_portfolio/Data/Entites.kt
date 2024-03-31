//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Database
//import androidx.room.Delete
//import androidx.room.Entity
//import androidx.room.Insert
//import androidx.room.PrimaryKey
//import androidx.room.Query
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.Update
//
//// UserEntity.kt
//@Entity(tableName = "users")
//data class UserEntity(
//    @PrimaryKey(autoGenerate = true)
//    val id: Long = 0,
//    val description: String,
//    val website: String,
//    val phoneNumber: String,
//    val location: String,
//    val skills: String,
//    val email: String
//)
//
//// UserDao.kt
//@Dao
//interface UserDao {
//    @Insert
//    suspend fun insertUser(user: UserEntity)
//
//    @Update
//    suspend fun updateUser(user: UserEntity)
//
//    @Delete
//    suspend fun deleteUser(user: UserEntity)
//
//    @Query("SELECT * FROM users")
//    fun getAllUsers(): LiveData<List<UserEntity>>
//}
//
//// AppDatabase.kt
//@Database(entities = [UserEntity::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        @Volatile
//        private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return instance ?: synchronized(this) {
//                val db = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "app_database"
//                ).build()
//                instance = db
//                db
//            }
//        }
//    }
//}
