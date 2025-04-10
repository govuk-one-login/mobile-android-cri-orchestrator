package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class SelectDocScreenId(
    override val rawId: String,
) : ScreenId {
    SelectPassport(rawId = "ccb1caa0-0331-4122-9f35-78ce28e0b4b5"),
    ConfirmPassport(rawId = "6bc099b5-8194-4d3f-86ed-cb815d8f0056"),
    ConfirmBrp(rawId = "cd406643-bfc3-4664-b3ac-f15c9f7469fa"),
    ConfirmDrivingLicence(rawId = "e112b83c-5100-4d44-8db0-bbdaaa5b19da"),
    SelectDrivingLicence(rawId = "2721b4e6-4b7e-4aab-bf50-5ba29f2d9d56"),
    TypesOfPhotoID(rawId = "c841ad80-4b16-4425-9bf2-aa24485fdaa0"),
    SelectBrp(rawId = "8349d732-fb9e-49f0-9b7c-5ed84ffe4613"),
    ConfirmNoChippedID(rawId = "e5259446-7367-4d97-8d4d-d8b10bb95eca")
}
