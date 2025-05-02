package uk.gov.onelogin.criorchestrator.testwrapper.hilt

import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.gov.android.network.client.GenericHttpClient
import uk.gov.onelogin.criorchestrator.testwrapper.SubjectTokenRepository
import uk.gov.onelogin.criorchestrator.testwrapper.network.createHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HttpClientHiltModule {
    @Provides
    @Singleton
    fun providesHttpClient(
        resources: Resources,
        subjectTokenRepository: SubjectTokenRepository,
    ): GenericHttpClient =
        createHttpClient(
            subjectTokenRepository = subjectTokenRepository,
            resources = resources,
        )
}
