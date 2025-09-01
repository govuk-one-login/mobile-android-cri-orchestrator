package uk.gov.onelogin.criorchestrator.features.handback.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class HandbackScreenId(
    override val rawId: String,
) : ScreenId {
    UnrecoverableError(rawId = "80069598-8c97-4789-96f8-8930fb633889"),

    ReturnToDesktopWeb(rawId = "fff9c5fe-3eb2-45d4-a07a-fb6d21582c50"),
    ReturnToMobileWeb(rawId = "4a9fafaa-5359-4105-b223-a51d71df435f"),

    ConfirmAbortDesktop(rawId = "5aa9f4a5-3312-42e4-b80a-432e237256a7"),
    ConfirmAbortMobile(rawId = "c4b6167a-09b3-4671-b9d7-30f7acd02bdb"),

    AbortedReturnToDesktopWeb(rawId = "8d09e79f-f242-48c8-a456-4f302c23e6cb"),

    FaceScanLimitReachedDesktop(rawId = "445f537d-c73c-46ba-b8df-e902a334a6d0"),
    FaceScanLimitReachedMobile(rawId = "40d79ac7-6193-4e67-8255-87893fd6b51a"),
}
