package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.fragment.app.FragmentActivity
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Named
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.android.securestore.authentication.AuthenticatorPromptConfiguration
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorAppScope

/**
 * Provides a [SecureStoreAsyncV2] based on configuration.
 *
 * When [SdkConfigKey.EnableSecureStore] is `false`, an in-memory store is used.
 * This supports unit tests where the Android KeyStore is unavailable.
 */
@SingleIn(CriOrchestratorAppScope::class)
@ContributesBinding(
    CriOrchestratorAppScope::class,
    binding =
        binding<
            @Named(SessionSecureStoreBindings.STORE_ID)
            SecureStoreAsyncV2,
        >(),
)
class SessionSecureStoreProvider(
    private val configStore: ConfigStore,
    @Named(SessionSecureStoreBindings.DEVICE_STORE_ID)
    private val deviceStore: Provider<SecureStoreAsyncV2>,
    private val inMemoryStore: Provider<InMemorySecureStoreAsyncV2>,
) : SecureStoreAsyncV2 {
    private fun store(): SecureStoreAsyncV2 =
        when (configStore.readSingle(SdkConfigKey.EnableSecureStore).value) {
            true -> deviceStore()
            false -> inMemoryStore()
        }

    override fun init(
        context: android.content.Context,
        configurationAsync: SecureStorageConfigurationAsync,
    ) = store().init(context, configurationAsync)

    override suspend fun upsert(
        key: String,
        value: String,
    ): String = store().upsert(key, value)

    override fun delete(key: String) = store().delete(key)

    override suspend fun deleteAll() = store().deleteAll()

    override suspend fun retrieve(vararg key: String): Map<String, String?> = store().retrieve(*key)

    override suspend fun retrieveWithAuthentication(
        vararg key: String,
        authPromptConfig: AuthenticatorPromptConfiguration,
        context: FragmentActivity,
    ): Map<String, String?> =
        store().retrieveWithAuthentication(
            key = key,
            authPromptConfig = authPromptConfig,
            context = context,
        )

    override fun exists(key: String): Boolean = store().exists(key)
}
