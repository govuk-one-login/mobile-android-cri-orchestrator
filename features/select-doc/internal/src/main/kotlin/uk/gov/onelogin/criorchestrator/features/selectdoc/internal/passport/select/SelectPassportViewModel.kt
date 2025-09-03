package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocAnalytics
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics.SelectDocScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(SelectPassportViewModel::class)
class SelectPassportViewModel @Inject constructor(
    private val analytics: SelectDocAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<SelectPassportAction>()
    val actions: Flow<SelectPassportAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = SelectDocScreenId.SelectPassport,
            title = SelectPassportConstants.titleId,
        )
    }

    fun onReadMoreClick() {
        analytics.trackButtonEvent(buttonText = SelectPassportConstants.readMoreButtonTextId)
        viewModelScope.launch {
            _actions.emit(SelectPassportAction.NavigateToTypesOfPhotoID)
        }
    }

    fun onConfirmSelection(selectedIndex: Int) {
        analytics.trackFormSubmission(
            buttonText = SelectPassportConstants.buttonTextId,
            response = SelectPassportConstants.options[selectedIndex],
        )

        viewModelScope.launch {
            when (selectedIndex) {
                0 -> _actions.emit(SelectPassportAction.NavigateToConfirmation)
                1 -> _actions.emit(SelectPassportAction.NavigateToBrp)
            }
        }
    }
}
