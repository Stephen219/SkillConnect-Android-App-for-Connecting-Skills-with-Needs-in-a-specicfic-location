package com.example.mob_dev_portfolio

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.Data.User

/**
 * This class is the adapter for the recycler view that displays the users in the search results
 * it takes in a list of users and displays them in the recycler view
 * it also handles the click events of the share and favorite buttons
 * it also handles the click event of the user card to view the user details in the UserDetailsActivity
 */


class UserAdapter(
    private val context: Context,


    private val userList: List<User>,
    param: (Any) -> Unit,
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    var onItemClick: ((User) -> Unit?)? = null
    val db by lazy { Userdb.getInstance(context) }

    /**
     * This class is the view holder for the user card
     * it takes in a view and initializes the views in the user card
     */



    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userName: TextView = itemView.findViewById(R.id.name_text)
        val userImage: ImageView = itemView.findViewById(R.id.love_button) // todo:  fro future, to handle the image you need glide  dependency added
        val userButton: ImageButton = itemView.findViewById(R.id.share_button)
        val favButton: ImageButton = itemView.findViewById(R.id.love_button)
        val location: TextView = itemView.findViewById(R.id.location_text)
        val skill: TextView = itemView.findViewById(R.id.skill_text)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.user_card_layout, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    /**
     * This function binds the user data to the user card
     * it also handles the click events of the share and favorite buttons
     * it also handles the click event of the user card to view the user details in the UserDetailsActivity
     * it also handles the favorite button click event to add or remove the user from the favorites
     */

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(userList[position])
            Toast.makeText(
                holder.itemView.context, "User clicked ${userList[position].name}",
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(holder.itemView.context, UserDetailsActivity::class.java)
            intent.putExtra("user", userList[position])
            holder.itemView.context.startActivity(intent)

        }
        val user = userList[position]

        if (getFavorites(holder.itemView.context).contains(user.id)) {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_242)
        }

        holder.userName.text = user.name
        holder.location.text = user.location
        holder.skill.text = user.skill
        // TODO 1: HANDLE THE CLICK EVENT OF THE SHARE BUTTON

        /**
         * This function handles the click event of the share button
         * it creates an intent to share the user details with other apps
         * it shares the user name, location, skill and phone number
         * in case of email, it also shares the email address and the subject of the email and the basic infoo
         */
        holder.userButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this user")
            intent.putExtra((Intent.EXTRA_EMAIL), arrayOf(user.email))
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this user: ${user.name} from ${user.location} with skill ${user.skill} and contact them at ${user.phone}"
            )
            holder.itemView.context.startActivity(Intent.createChooser(intent, "Share with"))
            val updatedFavorites = getFavorites(holder.itemView.context)

            println("Current favorites: ${updatedFavorites.joinToString(", ")}")


        }

        /**
         * this is where i handle the favorite button click event
         * it adds or removes the user from the favorites
         * it also updates the favorites in the shared preferences an adds the users info to the db
         * the db only stores the favorite users. storing all users in the db is not necessary as the users are fetched from the firebase database unless
         * i run .deleteAll() and .fetchAll() in the db immedeatly after an app starts and
         * */

        holder.favButton.setOnClickListener {
            toggleFavorite(holder.itemView.context, user)
            val updatedFavorites = getFavorites(holder.itemView.context)
            if (updatedFavorites.contains(user.id)) {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_24)
                Toast.makeText(
                    holder.itemView.context,
                    "Added ${user.name} to Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                holder.favButton.setImageResource(R.drawable.baseline_favorite_242)
                Toast.makeText(
                    holder.itemView.context,
                    "Removed ${user.name} from Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
            Log.d("Favorites", "Current favorites: ${updatedFavorites.joinToString(", ")}")
            Log.d(
                "debug",
                "currently we are hereeebebfhjebfjhrewgfeifghewukfbwfjhekfhwbekkewflhbfewkih"
            )
            println("Current favorites: ${updatedFavorites.joinToString(", ")}")
        }
    }

    /**
     * This function toggles the favorite status of a user
     * it adds or removes the user from the favorites
     * it also updates the favorites in the shared preferences an adds the users info to the db
     * *
     * */
    fun toggleFavorite(context: Context, user: User) {
        val sharedPrefs = context.getSharedPreferences("FavouritesPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        val favorites =
            sharedPrefs.getStringSet("favorites", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
        if (favorites.contains(user.id)) {
            favorites.remove(user.id)
            db!!.UserDao().deleteById(user.id)

        } else {
            favorites.add(user.id)
            db!!.UserDao().insert(user)

            Log.d("favoritesss",db!!.UserDao().getAllUsers().toString())
            Toast.makeText(context, "User added to favoritescvbvuyyegfefgy", Toast.LENGTH_SHORT).show()

        }
        editor.putStringSet("favorites", favorites)
        editor.apply()
    }
    /**
     * This function gets the favorites from the shared preferences
     * it returns a set of the favorite users
     * ideally, at first i thought i would only use shared pref for favourites, however,
     *  shared pref stores key value pair meaning only the id would be stored in the shared pref. therefore i decided to use the db.
     * */


    fun getFavorites(context: Context): Set<String> {
        val sharedPrefs = context.getSharedPreferences("FavouritesPrefs", Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet("favorites", mutableSetOf()) ?: mutableSetOf()
    }
}











