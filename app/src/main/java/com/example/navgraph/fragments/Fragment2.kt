package com.example.navgraph.fragments

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navgraph.R
import com.example.navgraph.databinding.Fragment2Binding
import com.example.navgraph.databinding.FragmentMainBinding
import com.example.navgraph.loadPlaylist
import com.example.navgraph.presention.ui.PlaylistAdapter
import com.example.navgraph.presention.ui.PlaylistState
import com.example.navgraph.presention.ui.PlaylistViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class Fragment2 : Fragment() {

    private lateinit var binding: Fragment2Binding
    private val viewModel by viewModel<PlaylistViewModel>()

    private val playlistAdapter = PlaylistAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=Fragment2Binding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycler.layoutManager = GridLayoutManager(requireContext(),2)

        binding.recycler.adapter = playlistAdapter

        viewModel.getAllPlaylist()


        lifecycleScope.launch {
            viewModel.state.collect{state->
                when (state){
                    is PlaylistState.Empty -> {
                        //
                    }
                    is PlaylistState.PlaylistLoaded -> {
                        val tracks = state.tracks
                        playlistAdapter.setTrackList(tracks)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPlaylist()
    }
    }
