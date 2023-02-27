package com.amarturelo.usersgithub.followers.core

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(
    JUnit4::class
)
abstract class BaseViewModelTest {

    @get:Rule
    val executeLiveDataInstantly = InstantTaskExecutorRule()
}