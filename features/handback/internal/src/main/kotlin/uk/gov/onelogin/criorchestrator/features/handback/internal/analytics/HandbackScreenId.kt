package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class HandbackScreenId(
    override val rawId: String,
) : ScreenId {
    ProblemError(rawId = "80069598-8c97-4789-96f8-8930fb633889"),
}
