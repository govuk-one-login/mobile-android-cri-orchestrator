package uk.gov.onelogin.criorchestrator.features.config.publicapi

import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ConfigTest {
    @Test
    fun `it can't be initialised with duplicate configuration`() {
        val key = StubStringConfigKey
        assertThrows<AssertionError> {
            Config(
                entries =
                    persistentListOf(
                        stubStringConfigEntry(
                            key = key,
                            value = "value 1",
                        ),
                        stubStringConfigEntry(
                            key = key,
                            value = "value 2",
                        ),
                    ),
            )
        }
    }

    @Test
    fun `it can be combined with other configuration`() {
        val expected =
            Config(
                entries =
                    persistentListOf(
                        stubStringConfigEntry(
                            value = "value that will be kept",
                        ),
                        stubBooleanConfigEntry(
                            value = true,
                        ),
                    ),
            )
        val actual =
            Config()
                .combinedWith(
                    Config(
                        entries =
                            persistentListOf(
                                stubStringConfigEntry(
                                    value = "value that will be replaced",
                                ),
                            ),
                    ),
                ).combinedWith(
                    Config(
                        entries =
                            persistentListOf(
                                stubStringConfigEntry(
                                    value = "value that will be kept",
                                ),
                            ),
                    ),
                ).combinedWith(
                    Config(
                        entries =
                            persistentListOf(
                                stubBooleanConfigEntry(
                                    value = true,
                                ),
                            ),
                    ),
                )
        assertEquals(expected, actual)
    }
}
