package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import app.cash.turbine.test
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.features.config.internal.FakeConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubConfig
import uk.gov.onelogin.criorchestrator.features.config.publicapi.stubStringConfigEntry

class DevMenuViewModelTest {
    private val viewModel =
        DevMenuViewModel(
            configStore =
                FakeConfigStore(
                    config = stubConfig(),
                ),
        )

    @Test
    fun `it emits initial state`() =
        runTest {
            viewModel.state.test {
                assertEquals(
                    DevMenuUiState(config = stubConfig()),
                    awaitItem(),
                )
            }
        }

    @Test
    fun `when an entry changes, it emits a new state`() =
        runTest {
            val oldEntry = stubStringConfigEntry()
            val newEntry =
                stubStringConfigEntry().copy(value = Config.Value.StringValue("updated value"))
            val expectedConfig =
                Config(
                    entries =
                        stubConfig()
                            .entries
                            .minus(oldEntry)
                            .plus(newEntry)
                            .toPersistentList(),
                )
            viewModel.state.test {
                skipItems(1)
                viewModel.onEntryChange(newEntry)
                val state = awaitItem()
                assertEquals(
                    DevMenuUiState(config = expectedConfig),
                    state,
                )
            }
        }
}
