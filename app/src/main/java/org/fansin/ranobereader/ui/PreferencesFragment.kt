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
import org.fansin.ranobereader.R
import org.fansin.ranobereader.preferences.PreferencesViewModel

class PreferencesFragment : Fragment() {

    private lateinit var preferencesViewModel: PreferencesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferencesViewModel = ViewModelProvider(this).get(PreferencesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_preferences, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        preferencesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}
