package uk.gov.onelogin.criorchestrator.libraries.analytics.resources

import android.content.Context
import androidx.annotation.StringRes
import dev.zacsweers.metro.Inject
import uk.gov.logging.api.analytics.extensions.getEnglishString

@Inject
class AndroidResourceProvider(
    private val context: Context,
) : ResourceProvider {
    override fun getEnglishString(
        @StringRes resId: Int,
    ): String = context.getEnglishString(resId)
}
