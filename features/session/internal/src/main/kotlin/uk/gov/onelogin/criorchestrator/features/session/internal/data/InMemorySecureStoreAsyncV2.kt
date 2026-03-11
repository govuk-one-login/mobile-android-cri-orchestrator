package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.fragment.app.FragmentActivity
import dev.zacsweers.metro.Inject
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.android.securestore.authentication.AuthenticatorPromptConfiguration

/**
 * In-memory [SecureStoreAsyncV2] for use when the Android KeyStore is unavailable.
 */
@Inject
class InMemorySecureStoreAsyncV2 : SecureStoreAsyncV2 {
    private val store = mutableMapOf<String, String>()

    override fun init(
        context: android.content.Context,
        configurationAsync: SecureStorageConfigurationAsync,
    ) {
        // No-op
    }

    override suspend fun upsert(
        key: String,
        value: String,
    ): String {
        store[key] = value
        return value
    }

    override fun delete(key: String) {
        store.remove(key)
    }

    override suspend fun deleteAll() {
        store.clear()
    }

    override suspend fun retrieve(vararg key: String): Map<String, String?> = key.associateWith { store[it] }

    override suspend fun retrieveWithAuthentication(
        vararg key: String,
        authPromptConfig: AuthenticatorPromptConfiguration,
        context: FragmentActivity,
    ): Map<String, String?> = throw UnsupportedOperationException("Authentication not supported by in-memory store")

    override fun exists(key: String): Boolean = store.containsKey(key)
}
