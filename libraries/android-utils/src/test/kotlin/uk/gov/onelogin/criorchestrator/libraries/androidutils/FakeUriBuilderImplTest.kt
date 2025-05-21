package uk.gov.onelogin.criorchestrator.libraries.androidutils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FakeUriBuilderImplTest {
    @Test
    fun `URI builder implementation returns desired URI`() {
        val result =
            FakeUriBuilderImpl().buildUri(
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
            FakeUriBuilderImpl().buildUri(
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
            FakeUriBuilderImpl().buildUri(
                "testBaseUri/?existingQueryKey=existingQueryValue",
                "testQueryKey",
                "testQueryValue%^&?/*£",
            )
        val expectedUri =
            "testBaseUri/?existingQueryKey=existingQueryValue&testQueryKey" +
                "=testQueryValue%25%5E%26%3F%2F*%C2%A3"
        assertEquals(expectedUri, result)
    }
}
