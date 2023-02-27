package com.amarturelo.usersgithub.followers.data.datasource.remote

import com.amarturelo.usersgithub.core.commons.utils.Either
import com.amarturelo.usersgithub.core.exception.Failure
import com.amarturelo.usersgithub.followers.data.api.FollowerAPI
import com.amarturelo.usersgithub.followers.data.model.FollowerModel
import com.amarturelo.usersgithub.followers.utils.FakeValuesModel
import com.amarturelo.usersgithub.followers.utils.providesGson
import com.amarturelo.usersgithub.followers.utils.providesOkHttpClientBuilder
import com.google.gson.Gson
import io.mockk.impl.annotations.InjectMockKs
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FollowerDataSourceRemoteTest {
    @get:Rule
    val mockWebServer = MockWebServer()

    lateinit var gson: Gson

    private lateinit var api: FollowerAPI

    @InjectMockKs
    private lateinit var dataSource: FollowerDataSourceRemote

    @Before
    fun setUp() {
        gson = providesGson()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(providesOkHttpClientBuilder())
            .build()
            .create(FollowerAPI::class.java)

        dataSource = FollowerDataSourceRemote(api)
    }

    @Test
    fun `given success Users when users then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesModel.users()
        mockWebServer.enqueue(MockResponse().apply {
            setBody(gson.toJson(fakeResult))
            setResponseCode(200)
        })

        //when
        val result = dataSource.followersByUsername("amarturelo")

        //then
        Assert.assertEquals(Either.Right(fakeResult), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given empty Response when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("[]")
            setResponseCode(200)
        })

        //when
        val result = dataSource.followersByUsername("amarturelo")

        //then
        Assert.assertEquals(Either.Right<List<FollowerModel>>(listOf()), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given fail result when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("")
            setResponseCode(500)
        })

        //when
        val result = dataSource.followersByUsername("amarturelo")

        //then
        Assert.assertEquals(result, Either.Left(Failure.UnknownError))
        Assert.assertTrue(result.isLeft)
        Assert.assertFalse(result.isRight)
    }
}