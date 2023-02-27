package com.amarturelo.usersgithub.followers.data.repository

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.commons.utils.getOrElse
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.data.datasource.FollowerDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import com.amarturelo.usersgithub.followers.utils.FakeValuesModel

class FollowerRepositoryDataTest {
    @MockK(relaxed = true)
    private lateinit var dataSource: FollowerDataSource

    @InjectMockKs
    private lateinit var repository: FollowerRepositoryData

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun notNullViewModel() {
        Assert.assertNotNull(repository)
    }

    @Test
    fun `given valid params when run then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesModel.users()
        coEvery { dataSource.followersByUsername(any()) } returns Either.Right(fakeResult)

        //when
        val result = repository.followersByUsername("amarturelo")

        //then
        Assert.assertEquals(fakeResult.size, result.getOrElse(listOf()).size)
    }

    @Test
    fun `given invalid params when run then verify result`() = runTest {
        //given
        coEvery { dataSource.followersByUsername(any()) } returns Either.Left(Failure.NetworkConnection)

        //when
        val result = repository.followersByUsername("amarturelo")

        //then
        Assert.assertTrue(result.isLeft)
    }
}