package com.example.mob_dev_portfolio



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.mob_dev_portfolio.Data.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

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

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

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
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {

            if (checkNetwork.isNetworkAvailable(requireContext())) {
                fetchUserData()
                Toast.makeText(context, "Refreshinng", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
                Toast.makeText(context, "Refreshed", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show()
                swipeRefreshLayout.isRefreshing = false
            }
        }

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

    /**
     * fetchUserData() function is used to fetch user data from the Firebase Realtime Database
     * qnd display the data in a recycler view
     */
     private fun fetchUserData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!isAdded) {

                    return
                }
                if (snapshot.exists()) {
                    var userData = snapshot.children.mapNotNull { it.getValue(User::class.java) }
                    val sameCityUsers = userData.filter { it.location == currentUserCity }
                    val otherCityUsers = userData.filter { it.location != currentUserCity }
                    var sortedData = sameCityUsers + otherCityUsers
                    userData = sortedData
                    val filteredData = userData.filter { it.id != authenticatedUserId }



                    userAdapter = UserAdapter(requireContext(),
                        filteredData) { user ->
                        val intent = Intent(requireContext(), UserDetailsActivity::class.java)
                        intent.putExtra("user", user.toString())
                        startActivity(intent)
                    }
                    recyclerView.adapter = userAdapter

                }
            }
            // if there is an error fetching data from the database, a toast message will be displayed
            override fun onCancelled(error: DatabaseError) {


                if (isAdded) {
                    Log.e("HomeFragment", "Firebase error: ${error.message}")
                    Toast.makeText(requireContext(), "Error fetching data", Toast.LENGTH_SHORT).show()
                }
                Log.e("HomeFragment", "Firebase error: ${error.message}")
                Toast.makeText(context, "Error fetching data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
