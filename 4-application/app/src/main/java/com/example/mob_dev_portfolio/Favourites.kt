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
import com.example.mob_dev_portfolio.Data.User


class Favourites : Fragment() {
    val db by lazy { Userdb.getInstance(requireContext()) }
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





        recyclerView = view.findViewById(R.id.recyclerFavs)
        recyclerView.layoutManager = LinearLayoutManager(context)


        val users = db?.UserDao()?.getAllUsers() ?: emptyList()
        if (users.isNotEmpty()) {
            recyclerView.visibility = View.VISIBLE
        }


        userAdapter = UserAdapter(requireContext(), users) { user ->
            val intent = Intent(requireContext(), UserDetailsActivity::class.java)
            intent.putExtra("user", user.toString()) // Assuming User implements Parcelable
            startActivity(intent)
        }
        recyclerView.adapter = userAdapter


        Log.d("Favourites", "onCreateView: Favourites Fragment")
        Log.d("Favourites",   db!!.UserDao().getAllUsers().toString())

        return view


    }


}