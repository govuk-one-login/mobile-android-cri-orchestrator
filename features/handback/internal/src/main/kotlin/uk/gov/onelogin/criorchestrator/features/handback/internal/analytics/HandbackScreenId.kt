package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class HandbackScreenId(
    override val rawId: String,
) : ScreenId {
    UnrecoverableError(rawId = "80069598-8c97-4789-96f8-8930fb633889"),

    ReturnToMobileWeb(rawId = "4a9fafaa-5359-4105-b223-a51d71df435f"),
}
