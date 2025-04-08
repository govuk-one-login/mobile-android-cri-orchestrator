package uk.gov.onelogin.criorchestrator.features.config.publicapi

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class ConfigEntryTest {
    @Test
    fun `valid boolean value`() {
        Config.Entry(
            key = StubBooleanConfigKey,
            value = Config.Value.BooleanValue(true),
        )
    }

    @Test
    fun `invalid boolean value type`() {
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
    fun `valid string value`() {
        Config.Entry(
            key = StubStringConfigKey,
            value = Config.Value.StringValue("hello, world"),
        )
    }

    @Test
    fun `invalid string value type`() {
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
    fun `valid option value`() {
        Config.Entry(
            key = StubOptionConfigKey,
            value = Config.Value.StringValue(StubOptionConfigKey.OPTION_1),
        )
    }

    @Test
    fun `invalid option value`() {
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
    fun `invalid option value type`() {
        assertThrows<IllegalArgumentException> {
            Config.Entry(
                key = StubOptionConfigKey,
                value = Config.Value.BooleanValue(true),
            )
        }
    }
}
