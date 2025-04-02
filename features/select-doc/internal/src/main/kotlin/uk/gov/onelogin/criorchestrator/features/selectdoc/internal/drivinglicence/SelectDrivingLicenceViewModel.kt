package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.drivinglicence

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uk.gov.idcheck.sdk.passport.nfc.checker.NfcChecker
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.nfc.NfcConfigKey
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocumentScreenId

internal class SelectDrivingLicenceViewModel(
    private val analytics: SelectDocumentAnalytics,
    private val nfcChecker: NfcChecker,
    private val configStore: ConfigStore,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectDrivingLicenceAction>()
    val actions: Flow<SelectDrivingLicenceAction> = _actions

    private val _isNfcEnabled = MutableStateFlow(false)
    val isNfcEnabled: StateFlow<Boolean> = _isNfcEnabled

    private val _state = MutableStateFlow(SelectedDrivingLicenceState())
    val state: StateFlow<SelectedDrivingLicenceState> = _state

    init {
        _isNfcEnabled.value = isNfcEnabled()
    }

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocumentScreenId.SelectDrivingLicence,
            title = SelectDrivingLicenceConstants.titleId,
        )

        _isNfcEnabled.value = isNfcEnabled()
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(SelectDrivingLicenceConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectDrivingLicenceAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onItemSelected(selectedItem: Int) {
        _state.update {
            it.copy(
                selectedItem = selectedItem,
            )
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
                        if (isNfcEnabled()) {
                            SelectDrivingLicenceAction.NavigateToNfcAbort
                        } else {
                            SelectDrivingLicenceAction.NavigateToNoNfcAbort
                        },
                    )
            }
        }
    }

    private fun isNfcEnabled() =
        if (configStore.readSingle(NfcConfigKey.StubNcfCheck).value) {
            configStore.readSingle(NfcConfigKey.IsNfcAvailable).value
        } else {
            nfcChecker.hasNfc()
        }
}
