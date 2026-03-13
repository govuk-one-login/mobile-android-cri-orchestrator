package uk.gov.onelogin.criorchestrator.sdk.internal.di

import android.content.Context
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import uk.gov.android.securestore.AccessControlLevel
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SharedPrefsStoreAsyncV2
import uk.gov.onelogin.criorchestrator.features.session.internal.data.SessionStoreBindings.STORE_ID
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope
import uk.gov.onelogin.criorchestrator.libraries.store.securestore.InMemorySecureStoreAsyncV2

@BindingContainer
@ContributesTo(CriOrchestratorAppScope::class)
class SecureStoreBindings {
    @Provides
    @SingleIn(CriOrchestratorAppScope::class)
    fun sharedPrefsSecureStore(context: Context): SharedPrefsStoreAsyncV2 =
        SharedPrefsStoreAsyncV2().apply {
            init(
                context,
                SecureStorageConfigurationAsync(
                    id = STORE_ID,
                    accessControlLevel = AccessControlLevel.OPEN,
                ),
            )
        }

    @Provides
    @SingleIn(CriOrchestratorAppScope::class)
    fun inMemorySecureStore(): InMemorySecureStoreAsyncV2 = InMemorySecureStoreAsyncV2()
}
