import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "users")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: String = "",
    val name: String,
    val email: String,
    val website: String,
    val phone: String,
    val skill: String,
    val location: String?): Parcelable {
    constructor() : this("", "","", "", "", "", "")

}



@Entity(tableName = "skills")
data class Skill(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val skillName: String
)

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
