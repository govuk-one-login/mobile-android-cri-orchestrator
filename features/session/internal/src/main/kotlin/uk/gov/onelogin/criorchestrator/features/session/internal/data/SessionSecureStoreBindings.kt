package uk.gov.onelogin.criorchestrator.features.session.internal.data

import android.content.Context
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import uk.gov.android.securestore.AccessControlLevel
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.android.securestore.SharedPrefsStoreAsyncV2
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

@BindingContainer
@ContributesTo(CriOrchestratorAppScope::class)
object SessionSecureStoreBindings {
    const val STORE_ID = "uk.gov.onelogin.criorchestrator.features.session.internal.data.session"

    @Provides
    @SingleIn(CriOrchestratorAppScope::class)
    @Named(STORE_ID)
    fun sessionSecureStore(context: Context): SecureStoreAsyncV2 =
        SharedPrefsStoreAsyncV2().apply {
            init(
                context,
                SecureStorageConfigurationAsync(
                    id = STORE_ID,
                    accessControlLevel = AccessControlLevel.OPEN,
                ),
            )
        }
}
