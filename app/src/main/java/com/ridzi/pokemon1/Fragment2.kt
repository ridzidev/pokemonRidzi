package com.ridzi.pokemon1

import android.app.ProgressDialog
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import androidx.appcompat.widget.SearchView


class Fragment2 : Fragment() {

    private val TAG = Fragment2::class.java.simpleName
    private lateinit var pDialog: ProgressDialog
    private lateinit var lv: ListView
    private lateinit var searchView: SearchView

    // URL to get Pokémon JSON data
    private val url = "https://pokeapi.co/api/v2/pokemon/?offset=0&limit=11000"
    private var pokemonList = ArrayList<HashMap<String, String>>()
    private val filteredPokemonList = ArrayList<HashMap<String, String>>()

    private lateinit var adapter: SimpleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment2, container, false)
        lv = view.findViewById(R.id.list)
        searchView = view.findViewById(R.id.searchView)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the ProgressDialog
        pDialog = ProgressDialog(context)
        pDialog.setMessage("Please wait... we collect all Pokémon for you...")
        pDialog.setCancelable(false)

        // Initialize the adapter
        adapter = SimpleAdapter(
            context,
            pokemonList,
            R.layout.list_item,
            arrayOf("name", "url"),
            intArrayOf(R.id.name, R.id.url)
        )

        lv.adapter = adapter

        lv.setOnItemClickListener { _, _, position, _ ->
            val clickedPokemon = pokemonList[position]
            val name = clickedPokemon["name"]
            val url = clickedPokemon["url"]
            Log.d("Fragment2", "Item url: $url")

            if (name != null) {
                if (url != null) {
                    showPokemonDetailDialog(name,url)
                }
            }
        }



        // Fetch Pokémon data from the API
        FetchPokemonData().execute()

        // Setup search functionality
        setupSearchView()
    }



    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Handle search submission if needed
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle search text changes and filter the list
                if (!newText.isNullOrBlank()) {
                    val filteredList = pokemonList.filter { it["name"]?.contains(newText, ignoreCase = true) == true }
                    adapter = SimpleAdapter(
                        requireContext(),
                        filteredList,
                        R.layout.list_item,
                        arrayOf("name", "url"),
                        intArrayOf(R.id.name, R.id.url)
                    )
                    lv.adapter = adapter
                } else {
                    adapter = SimpleAdapter(
                        requireContext(),
                        pokemonList,
                        R.layout.list_item,
                        arrayOf("name", "url"),
                        intArrayOf(R.id.name, R.id.url)
                    )
                    lv.adapter = adapter
                }

                // Set up click listener for the ListView
                lv.setOnItemClickListener { _, _, position, _ ->
                    if (position >= 0 && position < lv.adapter.count) {
                        val clickedPokemon = lv.adapter.getItem(position) as HashMap<*, *>
                        val name = clickedPokemon["name"] as String // Convert to non-nullable String
                        val url = clickedPokemon["url"] as String

                        Log.d("Fragment2", "Clicked search: $url")

                        if (name != null) {
                            if (url != null) {
                                showPokemonDetailDialog(name,url)
                            }
                        }

                    }
                }
                return true
            }
        })
    }

    private fun showPokemonDetailDialog(pokemonName: String, murl: String) {
        // Create a new instance of PokemonDetailDialogFragment
        val pokemonDetailDialogFragment = PokemonDetailDialogFragment()

        // Pass data to the dialog fragment using arguments
        val args = Bundle()
        args.putString("pokemonName", pokemonName)
        args.putString("murl", murl)
        // Add more data as needed

        // Extract the value from the URL
        val pattern = "/(\\d+)/$".toRegex()
        val matchResult = pattern.find(murl)
        val value = matchResult?.groupValues?.get(1)

        if (value != null) {
            args.putString("pokemonValue", value)
            Log.d("Fragment2", "Extracted value from $murl: $value")
        } else {
            Log.d("Fragment2", "Value not found in URL: $murl")
        }

        pokemonDetailDialogFragment.arguments = args

        // Show the dialog fragment
        val fragmentManager = requireActivity().supportFragmentManager
        pokemonDetailDialogFragment.show(fragmentManager, "pokemonDetailDialog")
    }


    private inner class FetchPokemonData : AsyncTask<Void, Void, Void>() {

        override fun onPreExecute() {
            super.onPreExecute()
            // Showing progress dialog
            pDialog.show()
        }

        override fun doInBackground(vararg params: Void): Void? {
            val httpHandler = HttpHandler()

            // Making a request to the URL and getting the response
            val jsonStr = httpHandler.makeServiceCall(url)

            Log.e(TAG, "Response from URL: $jsonStr")

            if (jsonStr != null) {
                try {
                    val jsonObj = JSONObject(jsonStr)

                    // Getting JSON Array node for results
                    val results = jsonObj.getJSONArray("results")

                    // Looping through all Pokémon
                    for (i in 0 until results.length()) {
                        val pokemon = results.getJSONObject(i)
                        val name = pokemon.getString("name")
                        val url = pokemon.getString("url")

                        // Extract the number from the URL
                        val number = url.split("/").filter { it.isNotEmpty() }.last()

                        // Construct the imgURL using the extracted number
                        val imgURL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$number.png"

                        // Temporary hash map for a single Pokémon
                        val pokemonData = HashMap<String, String>()

                        // Adding each field to HashMap
                        pokemonData["name"] = name
                        pokemonData["url"] = url
                        pokemonData["imgURL"] = imgURL // Store the modified imgURL

                        // Adding Pokémon data to the list
                        pokemonList.add(pokemonData)
                    }

                } catch (e: JSONException) {
                    Log.e(TAG, "Json parsing error: " + e.message)
                    activity?.runOnUiThread {
                        Toast.makeText(
                            activity?.applicationContext,
                            "Json parsing error: " + e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Log.e(TAG, "Couldn't get JSON from the server.")
                activity?.runOnUiThread {
                    Toast.makeText(
                        activity?.applicationContext,
                        "Couldn't get JSON from the server. Check LogCat for possible errors!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            // Dismiss the progress dialog
            if (pDialog.isShowing) {
                pDialog.dismiss()
            }

            // Notify the adapter that data has changed
            adapter.notifyDataSetChanged()
        }
    }
}
