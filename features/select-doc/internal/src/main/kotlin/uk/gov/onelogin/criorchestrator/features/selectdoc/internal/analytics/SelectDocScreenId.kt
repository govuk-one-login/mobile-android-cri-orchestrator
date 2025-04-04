package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class SelectDocScreenId(
    override val rawId: String,
) : ScreenId {
    SelectPassport(rawId = "ccb1caa0-0331-4122-9f35-78ce28e0b4b5"),
    TypesOfPhotoID(rawId = "c841ad80-4b16-4425-9bf2-aa24485fdaa0"),
    SelectBrp(rawId = "8349d732-fb9e-49f0-9b7c-5ed84ffe4613"),
}
