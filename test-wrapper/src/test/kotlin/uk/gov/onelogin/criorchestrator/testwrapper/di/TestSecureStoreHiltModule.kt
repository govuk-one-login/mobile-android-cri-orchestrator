package uk.gov.onelogin.criorchestrator.testwrapper.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import uk.gov.android.securestore.SecureStoreAsyncV2
import uk.gov.onelogin.criorchestrator.libraries.store.securestore.InMemorySecureStoreAsyncV2
import uk.gov.onelogin.criorchestrator.testwrapper.hilt.SecureStoreHiltModule
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SecureStoreHiltModule::class],
)
object TestSecureStoreHiltModule {
    @Provides
    @Singleton
    fun provideSecureStore(): SecureStoreAsyncV2 = InMemorySecureStoreAsyncV2()
}
