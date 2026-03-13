package uk.gov.onelogin.criorchestrator.libraries.store

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakePersistentStore<T> : Store<T> {
    private val state: MutableStateFlow<T?> = MutableStateFlow(null)

    override suspend fun read(): StateFlow<T?> = state.asStateFlow()

    override suspend fun write(value: T) {
        this.state.value = value
    }

    override suspend fun clear() {
        state.value = null
    }
}
