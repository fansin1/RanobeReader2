package org.fansin.ranobereader.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_novels.*
import org.fansin.ranobereader.App
import org.fansin.ranobereader.R
import org.fansin.ranobereader.network.ConnectionLiveData
import org.fansin.ranobereader.ui.novels.NovelsAdapter
import org.fansin.ranobereader.ui.novels.NovelsLoadingState
import org.fansin.ranobereader.ui.novels.NovelsViewModel
import javax.inject.Inject

class NovelsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var novelsLoadingState: LiveData<NovelsLoadingState>

    @Inject
    lateinit var connectionLiveData: ConnectionLiveData

    @Inject
    lateinit var novelsAdapter: NovelsAdapter

    private lateinit var navController: NavController

    private val novelsViewModel: NovelsViewModel by navGraphViewModels(R.id.mobile_navigation) {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_novels, container, false)
    }

    override fun onAttach(context: Context) {
        App.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        novelRecyclerView.layoutManager = LinearLayoutManager(
            context,
            RecyclerView.VERTICAL, false
        )
        novelRecyclerView.adapter = novelsAdapter

        navController = findNavController()

        swipeRefreshLayout.setOnRefreshListener {
            novelsViewModel.refresh()
        }

        initRecyclerViewObservers()
        initNetworkObservers()
    }

    override fun onPause() {
        super.onPause()
        saveRecyclerViewPosition()
    }

    private fun initRecyclerViewObservers() {
        novelsViewModel.layoutManagerState.observe(viewLifecycleOwner, Observer {
            novelRecyclerView.layoutManager?.onRestoreInstanceState(it)
        })
    }

    private fun initNetworkObservers() {
        connectionLiveData.observe(viewLifecycleOwner, Observer { connected ->
            if (!connected) {
                view?.let {
                    Snackbar
                        .make(it, "Lost Internet connection", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        })

        novelsLoadingState.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })
    }

    private fun saveRecyclerViewPosition() {
        novelsViewModel.layoutManagerState.value =
            novelRecyclerView.layoutManager?.onSaveInstanceState()
    }

    private fun updateView(state: NovelsLoadingState) {
        when (state) {
            NovelsLoadingState.LOADING -> {
                swipeRefreshLayout.isRefreshing = true
                failed.visibility = View.GONE
            }
            NovelsLoadingState.LOADED_INIT -> {
                swipeRefreshLayout.isRefreshing = false
                failed.visibility = View.GONE
            }
            NovelsLoadingState.ERROR_INIT -> {
                swipeRefreshLayout.isRefreshing = false
                failed.visibility = View.VISIBLE
            }
            else -> Log.d("ERROR", "WTF")
        }
    }
}
