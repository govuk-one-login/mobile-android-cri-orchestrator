package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.android.network.service.NetworkService
import uk.gov.onelogin.criorchestrator.testwrapper.SubjectTokenRepository
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientHiltModule {
    @Provides
    @Singleton
    fun providesNetworkService(
        resources: Resources,
        subjectTokenRepository: SubjectTokenRepository,
    ): NetworkService =
        createHttpClient(
            subjectTokenRepository = subjectTokenRepository,
            resources = resources,
        )
}
