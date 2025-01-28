package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import uk.gov.logging.api.analytics.logging.AnalyticsLogger

internal class ProveYourIdentityViewModel(
    private val analyticsLogger: AnalyticsLogger,
    @Suppress("UnusedPrivateProperty")
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    fun start() {

    }
}
