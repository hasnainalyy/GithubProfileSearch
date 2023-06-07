package com.example.speertechnologiesassessmentsolution.presentation.fragments

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.speertechnologiesassessmentsolution.R
import com.example.speertechnologiesassessmentsolution.databinding.FragmentHomeBinding
import com.example.speertechnologiesassessmentsolution.presentation.activity.MainActivity
import com.example.speertechnologiesassessmentsolution.presentation.view_model.HomeViewModel
import com.example.speertechnologiesassessmentsolution.utils.LoadImageBindingAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    val model: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)




        binding.swipeToRefresh.setOnRefreshListener {

            if (binding.etSearchByName.text.isNullOrEmpty()){
                Toast.makeText(context,"Enter username to fetch data",Toast.LENGTH_SHORT).show()
            }else{
                model.getUser(binding.etSearchByName.text.toString())
            }
            binding.swipeToRefresh.isRefreshing = false
        }


        binding.etSearchByName.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                if (binding.etSearchByName.text.isNullOrEmpty()){
                    Toast.makeText(context,"Enter username to fetch data",Toast.LENGTH_SHORT).show()
                }else{
                    model.getUser(binding.etSearchByName.text.toString())

                }

                return@OnEditorActionListener true
            }



            false
        })


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    model.user.collectLatest { data->
                        if (data.data != null) {
                            data.data.let {user ->
                                binding.etSearchByName.text.clear()
                                binding.profile.let {

                                    it.tvUsername.text = user.login
                                    it.tvBio.text = user.bio
                                    it.tvName.text = user.name
                                    it.tvFollowerCount.text = user.followers.toString()
                                    it.tvFollowingCount.text = user.following.toString()
                                    it.cvProfileData .visibility = View.VISIBLE
                                    LoadImageBindingAdapter.loadImage(it.userProfile,user.avatar_url)


                                    it.llFollowers.setOnClickListener {
                                        val action = HomeFragmentDirections.actionHomeFragmentToListFragment(user.login,"followers")
                                        findNavController().navigate(action)
                                    }

                                    it.llFollowing.setOnClickListener {
                                        val action = HomeFragmentDirections.actionHomeFragmentToListFragment(user.login,"following")
                                        findNavController().navigate(action)
                                    }

                            }



                            }
                        }else{
                            binding.profile.cvProfileData.visibility = View.GONE
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




}