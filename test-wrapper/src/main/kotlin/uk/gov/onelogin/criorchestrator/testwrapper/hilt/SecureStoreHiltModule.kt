package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.gov.android.securestore.AccessControlLevel
import uk.gov.android.securestore.SecureStorageConfigurationAsync
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.android.securestore.SharedPrefsStoreAsyncV2
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecureStoreHiltModule {
    private const val STORE_ID = "uk.gov.onelogin.criorchestrator.testwrapper"

    @Provides
    @Singleton
    fun provideSecureStore(
        @ApplicationContext
        context: Context,
    ): SecureStoreAsyncV2 =
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
