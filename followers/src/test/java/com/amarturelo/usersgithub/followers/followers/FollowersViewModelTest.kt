package com.amarturelo.usersgithub.followers.followers

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.core.BaseViewModelTest
import com.amarturelo.usersgithub.followers.core.CaptureObserver
import com.amarturelo.usersgithub.followers.domain.usecase.GetFollowersByUsernameUseCase
import com.amarturelo.usersgithub.followers.presentation.followers.FollowersViewModel
import com.amarturelo.usersgithub.followers.presentation.followers.UsersState
import com.amarturelo.usersgithub.followers.presentation.followers.UsersState.ERROR
import com.amarturelo.usersgithub.followers.presentation.followers.vo.FollowerListItemVO
import com.amarturelo.usersgithub.followers.utils.FakeValuesEntity
import com.amarturelo.usersgithub.followers.utils.FakeValuesVO
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FollowersViewModelTest : BaseViewModelTest() {
    @MockK(relaxed = true)
    lateinit var getFollowersByUsernameUseCase: GetFollowersByUsernameUseCase

    private lateinit var viewModel: FollowersViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(FollowersViewModel(getFollowersByUsernameUseCase))
    }

    @Test
    fun `given users when populate then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        viewModel.setUsers(FakeValuesVO.users())

        //when
        viewModel.initWithUsername("amarturelo")
        viewModel.populate()

        //then
        verify { getFollowersByUsernameUseCase(any(), any(), any()) }
        Assert.assertEquals(0, contentStateCapture.capture.size)
    }

    @Test
    fun `given empty users when populate then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)

        //when
        viewModel.initWithUsername("amarturelo")
        viewModel.populate()

        //then
        verify { getFollowersByUsernameUseCase(any(), any(), any()) }
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(UsersState.LOADING, contentStateCapture.capture[0])
    }

    @Test
    fun `given viewModel when refresh when verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)

        //when
        viewModel.initWithUsername("amarturelo")
        viewModel.refresh()

        //then
        verify { viewModel.populate() }
    }

    @Test
    fun `given get users success result when handleGetProductsUseCaseResult then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        val observerPayload = CaptureObserver<List<FollowerListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Right(FakeValuesEntity.users())
        )

        //then
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(1, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

    @Test
    fun `given get users failed result when handleGetProductsUseCaseResult then verify interactions`() {
        //given
        val contentStateCapture = CaptureObserver<String>()
        viewModel.contentState.observeForever(contentStateCapture)
        val observerPayload = CaptureObserver<List<FollowerListItemVO>>()
        val observerIsLoading = CaptureObserver<Boolean>()
        val observerFailure = CaptureObserver<Failure>()
        viewModel.users.observeForever(observerPayload)
        viewModel.isLoading.observeForever(observerIsLoading)
        viewModel.failure.observeForever(observerFailure)

        //when
        viewModel.handleGetUsersUseCaseResult(
            Either.Left(Failure.ServerError)
        )

        //then
        Assert.assertEquals(1, contentStateCapture.capture.size)
        Assert.assertEquals(1, observerFailure.capture.size)
        Assert.assertEquals(ERROR, contentStateCapture.capture.first())
        verify { viewModel.handleFailure(any()) }
        Assert.assertEquals(0, observerPayload.capture.size)
        Assert.assertEquals(false, observerIsLoading.capture.first())
    }

}