package uk.gov.onelogin.criorchestrator.libraries.store

import kotlinx.coroutines.flow.StateFlow

interface Store<T> {
    suspend fun read(): StateFlow<T?>

    suspend fun write(value: T)

    suspend fun clear()
}
