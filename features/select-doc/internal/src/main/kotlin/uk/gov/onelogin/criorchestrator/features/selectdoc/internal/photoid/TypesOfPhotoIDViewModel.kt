package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.photoid

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(TypesOfPhotoIDViewModel::class)
class TypesOfPhotoIDViewModel(
    private val analytics: SelectDocAnalytics,
    private val sessionStore: SessionStore,
    earliestAcceptableDrivingLicenceExpiryDate: EarliestAcceptableDrivingLicenceExpiryDate,
    configStore: ConfigStore,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            TypesOfPhotoIDState(
                earliestExpiryDate = earliestAcceptableDrivingLicenceExpiryDate(),
                enableExpiredBrp =
                    configStore.readSingle(SdkConfigKey.EnableExpiredBRP).value,
                disableBrpJourney = extractDisableBrpJourney(),
            ),
        )
    val state: StateFlow<TypesOfPhotoIDState> = _state

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.TypesOfPhotoID,
            title = TypesOfPhotoIDConstants.titleId,
        )
    }

    private fun extractDisableBrpJourney(): Boolean = sessionStore.read().value?.disableBrpJourney ?: false
}
