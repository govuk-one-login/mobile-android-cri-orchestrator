package uk.gov.onelogin.criorchestrator.libraries.analytics.resources

import android.content.Context
import androidx.annotation.StringRes
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import uk.gov.logging.api.analytics.extensions.getEnglishString
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class AndroidResourceProvider
    @Inject
    constructor(
        private val context: Context,
    ) : ResourceProvider {
        override fun getEnglishString(
            @StringRes resId: Int,
        ): String = context.getEnglishString(resId)
    }
