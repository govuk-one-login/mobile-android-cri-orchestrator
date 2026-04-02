package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.confirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.SdkConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.expiry.EarliestAcceptableDrivingLicenceExpiryDate
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(ConfirmDrivingLicenceViewModel::class)
class ConfirmDrivingLicenceViewModel(
    private val analytics: SelectDocAnalytics,
    earliestAcceptableExpiryDate: EarliestAcceptableDrivingLicenceExpiryDate,
    configStore: ConfigStore,
) : ViewModel() {
    private val _state =
        MutableStateFlow(
            ConfirmDrivingLicenceState(
                earliestExpiryDate = earliestAcceptableExpiryDate(),
                enableExpiredDrivingLicences =
                    configStore.readSingle(SdkConfigKey.EnableExpiredDrivingLicences).value,
            ),
        )
    val state: StateFlow<ConfirmDrivingLicenceState> = _state

    private val _action = MutableSharedFlow<ConfirmDrivingLicenceAction.NavigateToSyncIdCheck>()
    val action: Flow<ConfirmDrivingLicenceAction.NavigateToSyncIdCheck> = _action

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.ConfirmDrivingLicence,
            title = ConfirmDrivingLicenceConstants.titleId,
        )
    }

    fun onConfirmClick() {
        analytics.trackButtonEvent(buttonText = ConfirmDrivingLicenceConstants.buttonTextId)

        viewModelScope.launch {
            _action.emit(
                ConfirmDrivingLicenceAction.NavigateToSyncIdCheck,
            )
        }
    }
}
