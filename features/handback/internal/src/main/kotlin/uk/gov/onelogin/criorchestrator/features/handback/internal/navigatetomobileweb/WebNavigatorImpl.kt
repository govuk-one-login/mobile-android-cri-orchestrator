package uk.gov.onelogin.criorchestrator.features.handback.internal.navigatetomobileweb

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesBinding(CriOrchestratorScope::class)
class WebNavigatorImpl
    @Inject
    constructor(
        private val context: Context,
    ) : WebNavigator {
        override fun openWebPage(url: String) {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }
