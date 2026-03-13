package uk.gov.onelogin.criorchestrator.features.session.internal.data

import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.store.PersistentStore
import uk.gov.onelogin.criorchestrator.libraries.store.Store

@BindingContainer
@ContributesTo(CriOrchestratorAppScope::class)
object SessionStoreBindings {
    const val STORE_ID = "uk.gov.onelogin.criorchestrator.features.session.internal.data.session"
    const val STORE_KEY = "session"

    @Provides
    @SingleIn(CriOrchestratorAppScope::class)
    fun sessionStore(
        logger: Logger,
        coroutineScope: CoroutineScope,
        secureStore: SecureStoreAsyncV2,
    ): Store<Session> =
        PersistentStore(
            secureStore = secureStore,
            key = STORE_KEY,
            logger = logger,
            coroutineScope = coroutineScope,
            serializer = Session.serializer(),
        )
}
