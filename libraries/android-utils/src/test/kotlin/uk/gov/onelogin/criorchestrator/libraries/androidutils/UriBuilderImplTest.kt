package uk.gov.onelogin.criorchestrator.libraries.androidutils

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UriBuilderImplTest {
    @Test
    fun `URI builder implementation returns desired URI`() {
        val result =
            UriBuilderImpl().buildUri(
                "testBaseUri/",
                "testQueryKey",
                "testQueryValue",
            )
        val expectedUri = "testBaseUri/?testQueryKey=testQueryValue"
        assertEquals(expectedUri, result)
    }

    @Test
    fun `URI builder implementation returns desired URI when base URI when it has a query parameter`() {
        val result =
            UriBuilderImpl().buildUri(
                "testBaseUri/?existingQueryKey=existingQueryValue",
                "testQueryKey",
                "testQueryValue",
            )
        val expectedUri = "testBaseUri/?existingQueryKey=existingQueryValue&testQueryKey=testQueryValue"
        assertEquals(expectedUri, result)
    }

    @Test
    fun `URI builder implementation returns desired URI when query parameter has values to be encoded`() {
        val result =
            UriBuilderImpl().buildUri(
                "testBaseUri/?existingQueryKey=existingQueryValue",
                "testQueryKey",
                "testQueryValue%^&?/*£",
            )
        val expectedUri =
            "testBaseUri/?existingQueryKey=existingQueryValue&testQueryKey" +
                "=testQueryValue%25%5E%26%3F%2F*%C2%A3"
        assertEquals(expectedUri, result)
    }

    @Test
    fun `FakeUriBuilderImpl and real UriBuilderImpl have return the same string`() {
        val fakeResult =
            FakeUriBuilderImpl().buildUri(
                "testBaseUri/?existingQueryKey=existingQueryValue",
                "testQueryKey",
                "testQueryValue%^&?/*£",
            )
        val realResult =
            UriBuilderImpl().buildUri(
                "testBaseUri/?existingQueryKey=existingQueryValue",
                "testQueryKey",
                "testQueryValue%^&?/*£",
            )
        assertEquals(realResult, fakeResult)
    }
}
