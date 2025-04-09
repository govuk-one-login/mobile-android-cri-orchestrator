package uk.gov.onelogin.criorchestrator.features.error.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class ErrorScreenId(
    override val rawId: String,
) : ScreenId {
    RecoverableError(rawId = "80606f36-f6aa-4f49-aaa8-ff7d3cdeb16f"),
}
