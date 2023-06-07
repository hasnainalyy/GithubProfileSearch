package com.example.speertechnologiesassessmentsolution.presentation.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.speertechnologiesassessmentsolution.R
import com.example.speertechnologiesassessmentsolution.adapter.FollowerAdapter
import com.example.speertechnologiesassessmentsolution.databinding.FragmentListBinding
import com.example.speertechnologiesassessmentsolution.domain.model.UserModel
import com.example.speertechnologiesassessmentsolution.presentation.activity.MainActivity
import com.example.speertechnologiesassessmentsolution.presentation.view_model.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    val model: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val args: ListFragmentArgs by navArgs()
    private var followerAdapter: FollowerAdapter?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)

        when (args.intent) {
            "followers" -> {
                model.getFollowers(args.username)

            }
            else -> {
                model.getFollowing(args.username)
            }
        }


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    model.followers.collectLatest { data->
                        if (data.data != null) {
                            if (data.data.isNotEmpty()) {
                                binding.rvFollwers.visibility = View.VISIBLE
                                binding.txtNoResults.visibility = View.GONE

                                setFollowFollowerAdapter(data.data)


                            }else{
                                binding.rvFollwers.visibility = View.GONE
                                binding.txtNoResults.visibility = View.VISIBLE
                                binding.txtNoResults.text = "No ${args.intent}"
                            }
                        }

                        if (data.isLoading) {
                            binding.shimmerView.visibility = View.VISIBLE
                            binding.shimmerView.startShimmer()

                        } else {
                            binding.shimmerView.visibility = View.GONE
                            binding.shimmerView.stopShimmer()

                        }

                        if (data.error.isNotBlank()) {
                            Toast.makeText(context,data.error,Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                launch {
                    model.following.collectLatest { data->
                        if (data.data != null) {
                            if (data.data.isNotEmpty()) {
                                binding.rvFollwers.visibility = View.VISIBLE
                                binding.txtNoResults.visibility = View.GONE

                                setFollowFollowerAdapter(data.data)


                            }else{
                                binding.rvFollwers.visibility = View.GONE
                                binding.txtNoResults.visibility = View.VISIBLE
                                binding.txtNoResults.text = "No ${args.intent}"
                            }
                        }

                        if (data.isLoading) {
                            binding.shimmerView.visibility = View.VISIBLE
                            binding.shimmerView.startShimmer()

                        } else {
                            binding.shimmerView.visibility = View.GONE
                            binding.shimmerView.stopShimmer()

                        }

                        if (data.error.isNotBlank()) {
                            Toast.makeText(context,data.error,Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        return binding.root
    }


    private fun setFollowFollowerAdapter(data: ArrayList<UserModel>) {

        followerAdapter = FollowerAdapter(data,findNavController())

        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
            binding.rvFollwers.layoutManager = this
        }
        binding.rvFollwers.setHasFixedSize(true)

        binding.rvFollwers.adapter = followerAdapter


    }



}