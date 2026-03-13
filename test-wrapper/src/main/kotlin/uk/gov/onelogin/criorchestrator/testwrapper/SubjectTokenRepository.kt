package uk.gov.onelogin.criorchestrator.testwrapper

import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.builtins.serializer
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.libraries.store.PersistentStore
import uk.gov.onelogin.criorchestrator.testwrapper.hilt.CoroutinesHiltModule
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class SubjectTokenRepository
    @Inject
    constructor(
        logger: Logger,
        secureStore: SecureStoreAsyncV2,
        @Named(CoroutinesHiltModule.COROUTINE_SCOPE_NAME)
        coroutineScope: CoroutineScope,
    ) : PersistentStore<String>(
            secureStore = secureStore,
            key = KEY,
            logger = logger,
            coroutineScope = coroutineScope,
            serializer = String.serializer(),
        ) {
        companion object {
            private const val KEY = "uk.gov.onelogin.criorchestrator.testwrapper.subject_token"
        }
    }
