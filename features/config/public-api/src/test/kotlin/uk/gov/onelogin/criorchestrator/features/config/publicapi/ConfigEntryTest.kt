package uk.gov.onelogin.criorchestrator.features.config.publicapi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ConfigEntryTest {
    @Test
    fun `given valid boolean value, it doesn't throw`() {
        assertDoesNotThrow {
            Config.Entry(
                key = StubBooleanConfigKey,
                value = Config.Value.BooleanValue(true),
            )
        }
    }

    @Test
    fun `given invalid boolean value type, it throws`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Config.Entry(
                    key = StubBooleanConfigKey,
                    value = Config.Value.StringValue("not a boolean"),
                )
            }
        assertEquals("Value for stub boolean config key must be a BooleanValue", exception.message)
    }

    @Test
    fun `given valid string value, it doesn't throw`() {
        assertDoesNotThrow {
            Config.Entry(
                key = StubStringConfigKey,
                value = Config.Value.StringValue("hello, world"),
            )
        }
    }

    @Test
    fun `given invalid string value type, it throws`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Config.Entry(
                    key = StubStringConfigKey,
                    value = Config.Value.BooleanValue(true),
                )
            }
        assertEquals("Value for stub string config key must be a StringValue", exception.message)
    }

    @Test
    fun `given valid option value, it doesn't throw`() {
        assertDoesNotThrow {
            Config.Entry(
                key = StubOptionConfigKey,
                value = Config.Value.StringValue(StubOptionConfigKey.OPTION_1),
            )
        }
    }

    @Test
    fun `given invalid option value, it throws`() {
        val exception =
            assertThrows<IllegalArgumentException> {
                Config.Entry(
                    key = StubOptionConfigKey,
                    value = Config.Value.StringValue("not a valid option"),
                )
            }
        assertEquals(
            "Value for stub option config key must be one of [option 1, option 2, option 3]",
            exception.message,
        )
    }

    @Test
    fun `given invalid option value type, it throws`() {
        assertThrows<IllegalArgumentException> {
            Config.Entry(
                key = StubOptionConfigKey,
                value = Config.Value.BooleanValue(true),
            )
        }
    }
}
