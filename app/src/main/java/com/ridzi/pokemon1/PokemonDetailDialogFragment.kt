package com.ridzi.pokemon1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import java.net.URL

class PokemonDetailDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the custom layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Access the data passed via arguments
        val pokemonName = arguments?.getString("pokemonName")
        val murl = arguments?.getString("murl")
        val pokemonValue = arguments?.getString("pokemonValue")

        // Find the TextView and WebViews by their IDs
        val pokemonNameTextView = view.findViewById<TextView>(R.id.pokemonNameTextView)
//        val pokemonImageWebView = view.findViewById<WebView>(R.id.pokemonImageWebView)
        val pokemonDataWebView = view.findViewById<WebView>(R.id.pokemonDataWebView)

        // Display the Pokemon's name in the TextView
        pokemonNameTextView.text = pokemonName

//        val pokemonImageUrl =
//            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/$pokemonValue.svg"

        // Configure WebView settings for the image
//        val imageWebSettings: WebSettings = pokemonImageWebView.settings
//        imageWebSettings.javaScriptEnabled = true // Enable JavaScript if needed
//        imageWebSettings.domStorageEnabled = true
//
//        // Load the SVG image in the first WebView
//        pokemonImageWebView.loadUrl(pokemonImageUrl)

        // Configure WebView settings for loading URLs
        val dataWebSettings: WebSettings = pokemonDataWebView.settings
        dataWebSettings.javaScriptEnabled = true // Enable JavaScript if needed
        dataWebSettings.domStorageEnabled = true

        // Load the URL from the "murl" parameter into the second WebView

        val url = URL(murl)
        val pathComponents = url.path.split('/')

        // Remove empty components
        val nonEmptyComponents = pathComponents.filter { it.isNotEmpty() }

        // Get the last path component
        val lastPathComponent = nonEmptyComponents.lastOrNull()

        val urlString = "https://ridzidev.github.io/pokemon/index.html?apiurl=$lastPathComponent"


        if (murl != null) {
            pokemonDataWebView.loadUrl(urlString)
        }

        // Handle click on the close button
        val closeButton = view.findViewById<ImageView>(R.id.closeButton)
        closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        // Make the dialog full-screen
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT
        dialog?.window?.setLayout(width, height)
    }
}
