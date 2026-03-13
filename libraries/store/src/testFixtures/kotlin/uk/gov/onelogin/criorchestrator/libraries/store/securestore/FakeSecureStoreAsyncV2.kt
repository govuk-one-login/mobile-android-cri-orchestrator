package uk.gov.onelogin.criorchestrator.libraries.store.securestore

import android.content.Context
import androidx.fragment.app.FragmentActivity
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.android.securestore.authentication.AuthenticatorPromptConfiguration
import uk.gov.android.securestore.error.SecureStorageErrorV2

/**
 * Test double for [SecureStoreAsyncV2] that stores values in memory.
 */
class FakeSecureStoreAsyncV2 : SecureStoreAsyncV2 {
    private val store = mutableMapOf<String, String>()
    var throwOnRetrieve = false
    var throwOnUpsert = false
    var throwOnDelete = false
    var throwOnDeleteAll = false

    override fun init(
        context: Context,
        configurationAsync: SecureStorageConfigurationAsync,
    ) {
        // No-op for tests
    }

    override suspend fun upsert(
        key: String,
        value: String,
    ): String {
        if (throwOnUpsert) throw SecureStorageErrorV2(Exception("test upsert error"))
        store[key] = value
        return value
    }

    override fun delete(key: String) {
        if (throwOnDelete) throw SecureStorageErrorV2(Exception("test delete error"))
        store.remove(key)
    }

    override suspend fun deleteAll() {
        if (throwOnDeleteAll) throw SecureStorageErrorV2(Exception("test deleteAll error"))
        store.clear()
    }

    override suspend fun retrieve(vararg key: String): Map<String, String?> {
        if (throwOnRetrieve) throw SecureStorageErrorV2(Exception("test retrieve error"))
        return key.associateWith { store[it] }
    }

    override suspend fun retrieveWithAuthentication(
        vararg key: String,
        authPromptConfig: AuthenticatorPromptConfiguration,
        context: FragmentActivity,
    ): Map<String, String?> = throw UnsupportedOperationException("Not used in tests")

    override fun exists(key: String): Boolean = store.containsKey(key)

    fun hasData(): Boolean = store.isNotEmpty()
}
