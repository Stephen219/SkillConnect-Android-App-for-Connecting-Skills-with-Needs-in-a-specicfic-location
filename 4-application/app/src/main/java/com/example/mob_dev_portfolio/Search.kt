package com.example.mob_dev_portfolio


import addRecentSearch
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mob_dev_portfolio.Data.RecentSearchAdapter
import com.example.mob_dev_portfolio.Data.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import getRecentSearches
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class Search : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var searchScreen: RelativeLayout
    private lateinit var recyclerSearchResults: RecyclerView
    private lateinit var noResultsFound: LinearLayout


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        database = FirebaseDatabase.getInstance().reference.child("users")

        val editTextCity = view.findViewById<EditText>(R.id.editTextCity)
        val editTextProfession = view.findViewById<EditText>(R.id.editTextProfession)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)
        val recyclerRecentSearches = view.findViewById<RecyclerView>(R.id.recyclerRecentSearches)
        recyclerSearchResults = view.findViewById<RecyclerView>(R.id.recyclerSearchResults)
        searchScreen = view.findViewById(R.id.relativeLayoutSearch)
        noResultsFound = view.findViewById(R.id.noResultsFound)

        recyclerRecentSearches.layoutManager = LinearLayoutManager(requireContext())
        recyclerSearchResults.layoutManager = LinearLayoutManager(requireContext())
        buttonSearch.setOnClickListener {
            val city = editTextCity.text.toString().trim()
            val profession = editTextProfession.text.toString().trim()

            if (city.isNotEmpty() && profession.isNotEmpty()) {


                if (!checkNetwork.isNetworkAvailable(requireContext())) {
                    Toast.makeText(
                        requireContext(),
                        "No internet covffvfvffvfvnnection. Please check your network and try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener

                }

                val searchTerm = "$city, $profession"

                CoroutineScope(Dispatchers.IO).launch {
                    addRecentSearch(requireContext(), searchTerm)
                }

                performSearch(city, profession)
            } else {
                if (city.isEmpty()) {
                    editTextCity.error = "City is required"
                    editTextCity.requestFocus()
                    editTextCity.isFocusableInTouchMode = true
                    editTextCity.isFocusable = true

                }
                if (profession.isEmpty()) {
                    editTextProfession.error = "skill is required"
                }
            }
        }

        getRecentSearches(requireContext())
            .onEach { recentSearches ->
                val recentSearchList = recentSearches.toList().reversed()
                Log.d("Search", "Recent searches: $recentSearchList")
                Log.d("Search", "Recent searches size: ${recentSearchList.size}")
                val adapter = RecentSearchAdapter(recentSearchList) { search ->
                    val parts = search.split(", ")
                    editTextCity.setText(parts[0])
                    editTextProfession.setText(parts[1])
                }
                recyclerRecentSearches.adapter = adapter
            }
            .launchIn(CoroutineScope(Dispatchers.Main))

        return view
    }
    /**
     * This function performs the search operation
     * @param city The city to search for
     * @param profession / skills The profession to search for
     * @return
     */
    private fun performSearch(city: String, profession: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val users = mutableListOf<User>()
            database.get()
                .addOnSuccessListener { snapshot ->
                    for (child in snapshot.children) {
                        val user = child.getValue(User::class.java)
                        if (user != null && user.location == city && user.skill == profession) {
                            users.add(user)
                        }
                    }
                    requireActivity().runOnUiThread {
                        searchScreen.visibility = View.GONE
                        if (users.isEmpty()) {
                            noResultsFound.visibility = View.VISIBLE
                            recyclerSearchResults.visibility = View.GONE
                        } else {
                            noResultsFound.visibility = View.GONE
                            recyclerSearchResults.visibility = View.VISIBLE
                            val adapter = UserAdapter(requireContext(),

                                users
                            ) { user ->
                                val intent =
                                    Intent(requireContext(), UserDetailsActivity::class.java)
                                intent.putExtra("user", user.toString())
                                startActivity(intent)
                            }
                            recyclerSearchResults.adapter = adapter
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("SearchFragment", "Error fetching rhe data: ${exception.message}")
                }
        }

    }
}



