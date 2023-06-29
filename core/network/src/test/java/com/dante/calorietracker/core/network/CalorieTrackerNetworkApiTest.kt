package com.dante.calorietracker.core.network

import com.dante.calorietracker.core.testing.util.MainDispatcherRule
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import kotlin.test.assertFails

@OptIn(ExperimentalSerializationApi::class)
class CalorieTrackerNetworkApiTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var json: Json

    private lateinit var api: CalorieTrackerNetworkApi
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun initService() {
        json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }
        mockWebServer = MockWebServer()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(CalorieTrackerNetworkApi::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun searchFood_success_returnsListOfFood() = runTest {
        enqueueResponse("/ValidateFoodResponse.json", 200)
        val response = api.searchFood("yogurt", 1, 10)
        mockWebServer.takeRequest()

        val food = response.products[0]
        assertEquals(response.products.size, 10)
        assertEquals(food.productName, null)
    }

    @Test
    fun searchFood_invalidResponse_returnsFailure() = runTest {
        enqueueResponse("/ValidateFoodResponse.json", 403)
        assertFails { api.searchFood("yogurt", 1, 10) }
    }

    private fun enqueueResponse(
        fileName: String,
        code: Int,
        headers: Map<String, String> = emptyMap()
    ) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse
                .setResponseCode(code)
                .setBody(source?.readString(StandardCharsets.UTF_8) ?: "")
        )
    }
}
