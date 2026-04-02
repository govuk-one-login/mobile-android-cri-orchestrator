package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(TypesOfPhotoIDViewModel::class)
class TypesOfPhotoIDViewModel(
    private val analytics: SelectDocAnalytics,
    earliestAcceptableDrivingLicenceExpiryDate: EarliestAcceptableDrivingLicenceExpiryDate,
    configStore: ConfigStore,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            TypesOfPhotoIDState(
                earliestExpiryDate = earliestAcceptableDrivingLicenceExpiryDate(),
                enableExpiredDrivingLicences =
                    configStore.readSingle(SdkConfigKey.EnableExpiredDrivingLicences).value,
            ),
        )
    val state: StateFlow<TypesOfPhotoIDState> = _state

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.TypesOfPhotoID,
            title = TypesOfPhotoIDConstants.titleId,
        )
    }
}
