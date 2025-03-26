package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmation.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class ConfirmDocumentScreenId(
    override val rawId: String,
) : ScreenId {
    ConfirmPassport(rawId = "6bc099b5-8194-4d3f-86ed-cb815d8f0056"),
    ConfirmBrp(rawId = "cd406643-bfc3-4664-b3ac-f15c9f7469fa"),
    ConfirmDrivingLicence(rawId = "e112b83c-5100-4d44-8db0-bbdaaa5b19da"),
}
