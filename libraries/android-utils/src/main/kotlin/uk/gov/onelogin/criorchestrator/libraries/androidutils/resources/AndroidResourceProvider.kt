package uk.gov.onelogin.criorchestrator.libraries.androidutils.resources

import android.content.Context
import androidx.annotation.StringRes
import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.logging.api.analytics.extensions.getEnglishString
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

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
