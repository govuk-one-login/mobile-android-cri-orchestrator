package uk.gov.onelogin.criorchestrator.features.error.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class ErrorScreenId(
    override val rawId: String,
) : ScreenId {
    UnrecoverableError(rawId = "80069598-8c97-4789-96f8-8930fb633889"),
    RecoverableError(rawId = "80606f36-f6aa-4f49-aaa8-ff7d3cdeb16f"),
}
