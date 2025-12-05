package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(TypesOfPhotoIDViewModel::class)
class TypesOfPhotoIDViewModel(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.TypesOfPhotoID,
            title = TypesOfPhotoIDConstants.titleId,
        )
    }
}
