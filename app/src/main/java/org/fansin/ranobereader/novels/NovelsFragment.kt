package org.fansin.ranobereader.novels

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_novels.*
import org.fansin.ranobereader.R
import org.fansin.ranobereader.databinding.FragmentNovelsBinding
import org.fansin.ranobereader.domain.model.Novel
import javax.inject.Inject

class NovelsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val novelsViewModel: NovelsViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(NovelsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentNovelsBinding>(
            inflater, R.layout.fragment_novels, container, false)
        binding.viewModel = novelsViewModel
        binding.lifecycleOwner = this
        novelsViewModel.novelsAdapter.observe(viewLifecycleOwner,
            Observer<NovelsAdapter> {
                novels_recycler_view.adapter = it
            })

        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        novels_recycler_view.layoutManager = LinearLayoutManager(context,
            RecyclerView.VERTICAL, false)
        novels_recycler_view.adapter = novelsViewModel.novelsAdapter.value
    }
}