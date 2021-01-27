package com.picpay.desafio.android.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.util.CoroutineTestRule
import org.junit.Rule

abstract class BaseTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    val testDispatchersProvider by lazy {
        coroutineTestRule.testDispatcher
    }
}