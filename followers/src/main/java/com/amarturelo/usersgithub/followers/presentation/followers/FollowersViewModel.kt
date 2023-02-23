package com.amarturelo.usersgithub.followers.presentation.followers

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.commons.utils.getOrElse
import com.amarturelo.usersgithub.core.commons.utils.onFailure
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.core.presentation.SingleLiveEvent
import com.amarturelo.usersgithub.followers.domain.entity.FollowerEntity
import com.amarturelo.usersgithub.followers.domain.usecase.GetFollowersByUsernameUseCase
import com.amarturelo.usersgithub.followers.domain.usecase.GetUsersUseCaseParams
import com.amarturelo.usersgithub.followers.presentation.commons.StatefulLayout
import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO
import com.amarturelo.usersgithub.followers.presentation.followers.vo.toVO
import javax.inject.Inject

class FollowersViewModel @Inject constructor(
    private val getFollowersByUsernameUseCase: GetFollowersByUsernameUseCase,
) : ViewModel() {

    private var username: String? = null

    private val _goToDetails: SingleLiveEvent<FollowerListItemVO> = SingleLiveEvent()
    val goToDetails: LiveData<FollowerListItemVO> = _goToDetails

    private val _users: MutableLiveData<List<FollowerListItemVO>> = MutableLiveData()
    val users: LiveData<List<FollowerListItemVO>> = _users

    private val _failure: MutableLiveData<Failure> = SingleLiveEvent()
    val failure: LiveData<Failure> = _failure

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _contentState: MutableLiveData<String> = MutableLiveData()
    val contentState: LiveData<String> = _contentState

    fun populate() {
        assert(username != null) { "init viewModel first" }

        if (users.value.isNullOrEmpty()) {
            _contentState.value = UsersState.LOADING
        }
        getFollowersByUsernameUseCase(
            GetUsersUseCaseParams(username!!),
            viewModelScope,
            ::handleGetUsersUseCaseResult
        )
    }

    fun refresh() {
        this.populate()
    }

    @VisibleForTesting
    fun handleGetUsersUseCaseResult(result: Either<Failure, List<FollowerEntity>>) {
        if (result.isLeft) {
            _contentState.value = UsersState.ERROR
            result.onFailure {
                handleFailure(it)
            }
        } else {
            _contentState.value = UsersState.CONTENT
            val results = result.getOrElse(listOf())
            _users.value = results.map { it.toVO() }
        }
        _isLoading.value = false
    }

    @VisibleForTesting
    fun handleFailure(failure: Failure) {
        _failure.value = failure
    }

    @VisibleForTesting
    fun setUsers(users: List<FollowerListItemVO>) {
        _users.value = users
    }

    fun initWithUsername(username: String) {
        this.username = username
    }
}

object UsersState {
    const val CONTENT = StatefulLayout.State.CONTENT
    const val ERROR = "STATE_ERROR"
    const val LOADING = "STATE_LOADING"
}