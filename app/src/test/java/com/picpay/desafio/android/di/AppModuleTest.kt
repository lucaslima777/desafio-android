package com.picpay.desafio.android.di

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.picpay.desafio.android.ApplicationSession
import com.picpay.desafio.android.data.remote.PicPayService
import com.picpay.desafio.android.data.repository.UserRepository
import com.picpay.desafio.android.view.UserViewModel
import org.junit.*
import org.junit.rules.TestRule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.check.checkModules
import org.koin.test.inject
import org.mockito.Mockito

class AppModuleTest : KoinTest {

    private val picPayService: PicPayService by inject()
    private val repository: UserRepository by inject()
    private val viewModel: UserViewModel by inject()

    private val context: Context = Mockito.mock(ApplicationSession::class.java)

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        startKoin {
            androidContext(context)
            loadKoinModules(appModule)
        }
    }

    @Test
    fun `Verifica modulos`() = getKoin().checkModules()

    @Test
    fun `Verifica instancia criada PicPayService`() {
        Assert.assertNotNull(picPayService)
    }

    @Test
    fun `Verifica instancia criada UserRepository`() {
        Assert.assertNotNull(repository)
    }

    @Test
    fun `Verifica instancia criada UserViewModel`() {
        Assert.assertNotNull(viewModel)
    }

    @After
    fun tearDown() = stopKoin()
}