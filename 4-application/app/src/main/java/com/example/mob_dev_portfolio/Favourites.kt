package com.example.mob_dev_portfolio

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


/**
 * this is the Favourites fragment that displays the users that the user has favourited
 * it uses the UserAdapter to display the users in a recycler view
 * it gets the users from the database(room) and displays them in the recycler view. the user can unfavourite a user and also access them offline
 * the user can click on a user to view their details
 */


class Favourites : Fragment() {
    private val  db by lazy { Userdb.getInstance(requireContext()) }
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Favourites", "onCreateView: Favourites Fragment")
        Log.d("Favourites",   db!!.UserDao().getAllUsers().toString())
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.recyclerFavs)
        recyclerView.layoutManager = LinearLayoutManager(context)
        // get all the users from the database
        val users = db?.UserDao()?.getAllUsers() ?: emptyList()
        if (users.isNotEmpty()) {
            // if there are users in the database, display them in the recycler view else an svg illustration rather than a blank screen
            recyclerView.visibility = View.VISIBLE
        }


        userAdapter = UserAdapter(requireContext(), users) { user ->
            val intent = Intent(requireContext(), UserDetailsActivity::class.java)
            // ideally, the user object should be passed to the next activity as a parcelable/serializable object  but it dint work so i passed it as a string
            intent.putExtra("user", user.toString())// pass the user object to the next activity as a string
            startActivity(intent)
        }
        recyclerView.adapter = userAdapter
        Log.d("Favourites", "onCreateView: Favourites Fragment")
        Log.d("Favourites",   db!!.UserDao().getAllUsers().toString())
        return view

    }

}