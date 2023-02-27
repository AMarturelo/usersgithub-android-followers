package com.amarturelo.usersgithub.followers.usecase

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.commons.utils.getOrElse
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.domain.repository.FollowerRepository
import com.amarturelo.usersgithub.followers.domain.usecase.GetFollowersByUsernameUseCase
import com.amarturelo.usersgithub.followers.domain.usecase.GetFollowersByUsernameUseCaseParams
import com.amarturelo.usersgithub.followers.utils.FakeValuesEntity
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetFollowersByUsernameUseCaseTest {

    @MockK(relaxed = true)
    lateinit var followerRepository: FollowerRepository

    @InjectMockKs
    lateinit var useCase: GetFollowersByUsernameUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given valid params when run then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesEntity.users()
        coEvery { followerRepository.followersByUsername(any()) } returns Either.Right(fakeResult)

        //when
        val result = useCase.run(GetFollowersByUsernameUseCaseParams("amarturelo"))

        //then
        Assert.assertEquals(fakeResult.size, result.getOrElse(listOf()).size)
    }

    @Test
    fun `given invalid params when run then verify result`() = runTest {
        //given
        coEvery { followerRepository.followersByUsername(any()) } returns Either.Left(Failure.NetworkConnection)

        //when
        val result = useCase.run(GetFollowersByUsernameUseCaseParams("amarturelo"))

        //then
        Assert.assertTrue(result.isLeft)
    }

}