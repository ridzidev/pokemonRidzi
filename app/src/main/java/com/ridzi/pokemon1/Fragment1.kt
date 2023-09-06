package com.ridzi.pokemon1

//import Fragment2
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.ridzi.pokemon1.R  // Make sure to import your app's R class.

class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)

        // Find the button in Fragment1 and set a click listener to open Fragment2
        val openFragment2Button = view.findViewById<Button>(R.id.openFragment2Button)
        openFragment2Button.setOnClickListener {
            val fragmentTransaction = parentFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, Fragment2())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        return view
    }
}
