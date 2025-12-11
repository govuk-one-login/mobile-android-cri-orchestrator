package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(DevMenuViewModel::class)
class DevMenuViewModel(
    private val configStore: ConfigStore,
) : ViewModel() {
    private val _state =
        MutableStateFlow<DevMenuUiState>(
            DevMenuUiState(
                entries = persistentListOf(),
            ),
        )
    val state: StateFlow<DevMenuUiState> = _state

    init {
        viewModelScope.launch {
            configStore.readAll().collect { config ->
                _state.value = _state.value.copy(entries = config.toUiEntries())
            }
        }
    }

    fun onEntryChange(entry: Config.Entry<Config.Value>) = configStore.write(entry)

    private fun Config.toUiEntries(): ImmutableList<Config.Entry<*>> =
        entries
            .filterNot {
                // Hide entries where the dependent configuration is not enabled
                it.key.dependsOn?.let { this[it].value } == false
            }.sortedWith { a, b ->
                compareConfigKeys(a.key, b.key)
            }.toPersistentList()
}

/**
 * Sorts entries by their dependency hierarchy and then alphabetically.
 */
fun compareConfigKeys(
    a: ConfigKey<*>,
    b: ConfigKey<*>,
): Int {
    val aDepth = a.depth()
    val bDepth = b.depth()
    return when {
        // Do these have a common parent?
        a.dependsOn == b.dependsOn -> a.name.compareTo(b.name)

        // Are these directly dependent on each other?
        a.dependsOn == b -> 1
        b.dependsOn == a -> -1

        // Search for a common parent key or direct dependency
        aDepth > bDepth -> compareConfigKeys(a.dependsOn!!, b)
        aDepth < bDepth -> compareConfigKeys(a, b.dependsOn!!)
        else -> compareConfigKeys(a.dependsOn!!, b.dependsOn!!)
    }
}

fun ConfigKey<*>.depth(): Int {
    var key: ConfigKey<*>? = this
    var depth = 0
    while (key?.dependsOn != null) {
        key = key.dependsOn
        depth++
    }
    return depth
}
