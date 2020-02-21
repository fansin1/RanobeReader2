package org.fansin.ranobereader.novels

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_novels.*
import org.fansin.ranobereader.R
import javax.inject.Inject

class NovelsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var novelsNetworkState: LiveData<NovelsNetworkState>

    private val novelsViewModel: NovelsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NovelsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_novels, container, false)

        novelsViewModel.novelsAdapter.observe(viewLifecycleOwner,
            Observer<NovelsAdapter> {
                novelsRecyclerView.adapter = it
            })

        novelsNetworkState.observe(viewLifecycleOwner, Observer {
            updateView(it)
        })

        return root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        novelsRecyclerView.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL, false)
        novelsRecyclerView.adapter = novelsViewModel.novelsAdapter.value

        swipeRefreshLayout.setOnRefreshListener {
            novelsViewModel.refresh()
        }

        novelsViewModel.pagedList.observe(viewLifecycleOwner, Observer {
            novelsViewModel.novelsAdapter.value!!.submitList(it)
        })
    }

    private fun updateView(state: NovelsNetworkState) {
        when(state) {
            NovelsNetworkState.LOADING -> {
                swipeRefreshLayout.isRefreshing = true
                failed.visibility = View.GONE
            }
            NovelsNetworkState.LOADED_AFTER -> {

            }
            NovelsNetworkState.LOADED_INIT -> {
                swipeRefreshLayout.isRefreshing = false
                failed.visibility = View.GONE
            }
            NovelsNetworkState.ERROR_INIT -> {
                swipeRefreshLayout.isRefreshing = false
                failed.visibility = View.VISIBLE
            }
            NovelsNetworkState.ERROR_AFTER -> {

            }
        }
    }
}