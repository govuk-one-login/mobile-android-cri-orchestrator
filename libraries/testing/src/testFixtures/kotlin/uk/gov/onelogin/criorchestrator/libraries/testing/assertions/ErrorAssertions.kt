package uk.gov.onelogin.criorchestrator.libraries.testing.assertions

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertInstanceOf

inline fun <reified ExpectedT : Throwable, ActualT : Throwable> assertExceptionEquals(
    expected: ExpectedT,
    actual: ActualT,
) {
    assertInstanceOf<ExpectedT>(ExpectedT::class.java, actual)
    Assertions.assertEquals(expected.message, actual.message)
}
