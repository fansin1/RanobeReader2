package org.fansin.ranobereader.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.fansin.ranobereader.App
import org.fansin.ranobereader.R
import org.fansin.ranobereader.domain.repository.FavoritesRepository
import org.fansin.ranobereader.ui.novels.NovelClicksBinder
import org.fansin.ranobereader.ui.novels.favorites.FavoritesViewModel
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var favoritesRepository: FavoritesRepository

    private lateinit var navController: NavController

    private val favoritesViewModel: FavoritesViewModel
            by navGraphViewModels(R.id.mobile_navigation) {
                viewModelFactory
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        //favoritesViewModel.adapter.value?.setClicksBinder(NovelClicksBinder(favoritesRepository))
        favoritesRecyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL, false
        )
        initObservers()
    }

    private fun initObservers() {
        favoritesViewModel.layoutManagerState.observe(viewLifecycleOwner, Observer {
            favoritesRecyclerView.layoutManager?.onRestoreInstanceState(it)
        })
        favoritesViewModel.adapter.observe(viewLifecycleOwner, Observer { adapter ->
            favoritesRecyclerView.adapter = adapter
        })
    }
}
