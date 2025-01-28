package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.ViewModelProvider
import com.squareup.anvil.annotations.ContributesTo
import uk.gov.logging.api.analytics.logging.AnalyticsLogger
import uk.gov.onelogin.criorchestrator.libraries.di.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Named

@ActivityScope
@ContributesTo(CriOrchestratorScope::class)
interface ProveYourIdentityComponent {
    @Named(ProveYourIdentityViewModelModule.FACTORY_NAME)
    fun proveIdentityViewModelFactory(
       // analyticsLogger: AnalyticsLogger,
    ): ViewModelProvider.Factory
}
