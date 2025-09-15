package uk.gov.onelogin.criorchestrator.features.error.internal.recoverableerror

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import uk.gov.onelogin.criorchestrator.features.error.internal.analytics.ErrorAnalytics
import uk.gov.onelogin.criorchestrator.features.error.internal.analytics.ErrorScreenId
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.CriOrchestratorViewModelScope
import uk.gov.onelogin.criorchestrator.libraries.di.viewmodel.ViewModelKey

@ContributesIntoMap(CriOrchestratorViewModelScope::class, binding = binding<ViewModel>())
@ViewModelKey(RecoverableErrorViewModel::class)
class RecoverableErrorViewModel @Inject constructor(
    private val analytics: ErrorAnalytics,
) : ViewModel() {
    private val _actions = MutableSharedFlow<RecoverableErrorAction>()
    val actions: Flow<RecoverableErrorAction> = _actions

    fun onScreenStart() {
        analytics.trackScreen(
            id = ErrorScreenId.RecoverableError,
            title = RecoverableErrorConstants.titleId,
        )
    }

    fun onButtonClick() {
        analytics.trackButtonEvent(
            buttonText = RecoverableErrorConstants.buttonTextId,
        )

        viewModelScope.launch {
            _actions.emit(RecoverableErrorAction.NavigateBack)
        }
    }
}
