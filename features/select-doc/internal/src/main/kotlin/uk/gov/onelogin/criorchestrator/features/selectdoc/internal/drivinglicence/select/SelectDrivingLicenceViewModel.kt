package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nfc.NfcChecker
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope

@ContributesIntoMap(CriOrchestratorScope::class)
@ViewModelKey(SelectDrivingLicenceViewModel::class)
class SelectDrivingLicenceViewModel(
    private val analytics: SelectDocAnalytics,
    private val nfcChecker: NfcChecker,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectDrivingLicenceAction>()
    val actions: Flow<SelectDrivingLicenceAction> = _actions

    private val _state =
        MutableStateFlow(
            SelectDrivingLicenseState(
                displayReadMoreButton = nfcChecker.hasNfc(),
            ),
        )
    val state: StateFlow<SelectDrivingLicenseState> = _state

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.SelectDrivingLicence,
            title = SelectDrivingLicenceConstants.titleId,
        )

        updateNfcEnabledState()
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(SelectDrivingLicenceConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectDrivingLicenceAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onContinueClicked(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = SelectDrivingLicenceConstants.buttonTextId,
            response = SelectDrivingLicenceConstants.options[selectedIndex],
        )

        viewModelScope.launch {
            when (selectedIndex) {
                0 -> _actions.emit(SelectDrivingLicenceAction.NavigateToConfirmation)
                1 ->
                    _actions.emit(
                        if (nfcChecker.hasNfc()) {
                            SelectDrivingLicenceAction.NavigateToConfirmNoChippedID
                        } else {
                            SelectDrivingLicenceAction.NavigateToConfirmNoNonChippedID
                        },
                    )
            }
        }
    }

    private fun updateNfcEnabledState() {
        _state.update { it.copy(displayReadMoreButton = nfcChecker.hasNfc()) }
    }
}
