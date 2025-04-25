package uk.gov.onelogin.criorchestrator.idcheckwrapper.sdkconfig

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.idcheck.network.api.backend.BackendApi
import uk.gov.idcheck.network.testdouble.backend.BackendApiDev
import javax.inject.Singleton

class IdCheckSdkHiltConfig {
    @InstallIn(SingletonComponent::class)
    @Module
    object BackendApiModule {
        @Provides
        @Singleton
        fun providesBackendApi(api: BackendApiDev): BackendApi = api
    }
}
