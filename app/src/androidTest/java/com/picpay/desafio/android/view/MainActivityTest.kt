package com.picpay.desafio.android.view

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.picpay.desafio.android.R
import com.picpay.desafio.android.di.appModule
import com.picpay.desafio.android.util.RecyclerViewMatchers
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules

@LargeTest
class MainActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private lateinit var activity: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        server.start()
        loadKoinModules(appModule(server.url("").toString()))
        activity = launchActivity<MainActivity>()
    }

    @Test
    fun shouldDisplayTitle() {
        requestSuccess()
        activity.apply {
            val expectedTitle = context.getString(R.string.title)

            moveToState(Lifecycle.State.RESUMED)

            onView(withText(expectedTitle)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun shouldDisplayListItem() {
        requestSuccess()
        activity.apply {
            val expectedName = "Eduardo Santos"
            val expectedUserName = "@eduardo.santos"

            moveToState(Lifecycle.State.RESUMED)

            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                FIRST_POSITION,
                withText(expectedName)
            )
            RecyclerViewMatchers.checkRecyclerViewItem(
                R.id.recyclerView,
                FIRST_POSITION,
                withText(expectedUserName)
            )
        }
        server.close()
    }

    @Test
    fun shouldDisplayError() {
        requestError()
        activity.apply {
            moveToState(Lifecycle.State.RESUMED)
            val expectedError = context.getString(R.string.error)
            onView(withText(expectedError))
        }
        server.close()
    }

    private fun requestSuccess() {
        val successCode = 200
        val body =
            "[{\"id\":1001,\"name\":\"Eduardo Santos\",\"img\":\"https://randomuser.me/api/portraits/men/9.jpg\",\"username\":\"@eduardo.santos\"}]"

        mockRequest(statusCode = successCode, body = body)
    }

    private fun requestError() {
        val errorCode = 404
        mockRequest(statusCode = errorCode)
    }


    private fun mockRequest(statusCode: Int, body: String = "") {
        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(body)
            }
        )
    }

    companion object {
        const val FIRST_POSITION = 0
    }
}