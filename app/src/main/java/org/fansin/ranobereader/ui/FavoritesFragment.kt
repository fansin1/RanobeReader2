package org.fansin.ranobereader.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.fansin.ranobereader.App
import org.fansin.ranobereader.R
import org.fansin.ranobereader.favorites.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favorites, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        favoritesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }
}
