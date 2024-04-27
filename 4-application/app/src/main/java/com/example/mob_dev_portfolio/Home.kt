package com.example.mob_dev_portfolio

import User
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.Data.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * in this class fragment it is the home fragment and it is the launcher fragment
 *  the users's' card containing a picture name , skill and location will be displayed here
 *  a click on the card will take the user to the user's profile for the users information
 *  the user's information will be displayed in the profile fragment
 *  the user's profile will contain the user's name, email, phone, website, skill and location, and an about
 */


class Home : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = FirebaseDatabase.getInstance().reference.child("users")

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)








        fetchUserData()


        return view
    }

    /**
     * fetchUserData() function is used to fetch user data from the Firebase Realtime Database
     * and log the data to the console
     *
     */
    private fun getCurrentFirebaseUser(): FirebaseUser? {
        val auth = FirebaseAuth.getInstance()
        return auth.currentUser
    }
    private fun getCurrentUserId(): String? {
        val currentUser = getCurrentFirebaseUser()
        return currentUser?.uid
    }
    val authenticatedUserId = getCurrentUserId()
    val currentUserCity : String = ""


    private fun fetchUserData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    var userData = snapshot.children.mapNotNull { it.getValue(User::class.java) }
                    val sameCityUsers = userData.filter { it.location == currentUserCity }
                    val otherCityUsers = userData.filter { it.location != currentUserCity }
                    var sortedData = sameCityUsers + otherCityUsers
                    userData = sortedData
                    val filteredData = userData.filter { it.id != authenticatedUserId }



                    userAdapter = UserAdapter(filteredData) { user ->
                        val intent = Intent(requireContext(), UserDetailsActivity::class.java)
                        intent.putExtra("user", user.toString())
                        startActivity(intent)
                    }
                    recyclerView.adapter = userAdapter

                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("HomeFragment", "Firebase error: ${error.message}")
            }
        })
    }
}
