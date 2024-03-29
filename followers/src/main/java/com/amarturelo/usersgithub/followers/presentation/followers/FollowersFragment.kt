package com.amarturelo.usersgithub.followers.presentation.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.core.exception.failure
import com.amarturelo.usersgithub.core.exception.observe
import com.amarturelo.usersgithub.followers.R
import com.amarturelo.usersgithub.followers.commons.Constants.ARG.USERNAME
import com.amarturelo.usersgithub.followers.databinding.FragmentFollowersBinding
import com.amarturelo.usersgithub.followers.presentation.followers.UsersState.ERROR
import com.amarturelo.usersgithub.followers.presentation.followers.UsersState.LOADING
import com.amarturelo.usersgithub.followers.presentation.followers.adapter.FollowersController
import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private val viewModel: FollowersViewModel by viewModels()

    // val args: FollowersFragmentArgs by navArgs()

    private lateinit var controller: FollowersController

    private var _binding: FragmentFollowersBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = arguments?.getString(USERNAME) ?: "amarturelo"

        viewModel.initWithUsername(username)
        viewModel.populate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupStatefulLayout()

        with(viewModel) {
            observe(users, ::handleUsers)
            observe(contentState, ::handleContentState)
            observe(isLoading, ::handleIsLoading)
            failure(failure, ::handleFailure)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        controller = FollowersController(::handleItemPressed)
        binding.rvFollowers.adapter = controller.adapter
        binding.srlFollowers.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupStatefulLayout() {
        binding.slFollowers.setStateView(
            ERROR,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_users_state_error,
                null,
            ),
        )

        binding.slFollowers.setStateView(
            LOADING,
            LayoutInflater.from(requireContext()).inflate(
                R.layout.layout_users_state_loading,
                null,
            ),
        )
    }

    private fun handleUsers(values: List<FollowerListItemVO>?) {
        values ?: return
        controller.setData(values)
    }

    private fun handleFailure(failure: Failure?) {
        failure ?: return
    }

    private fun handleIsLoading(isLoading: Boolean?) {
        binding.srlFollowers.isRefreshing = isLoading ?: false
    }

    private fun handleContentState(contentState: String?) {
        contentState ?: return
        binding.slFollowers.state = contentState
    }

    private fun handleItemPressed(item: FollowerListItemVO) {
    }
}
