package uk.gov.onelogin.criorchestrator.features.resume.internal.analytics

import uk.gov.onelogin.criorchestrator.libraries.analytics.ScreenId

enum class ResumeScreenId(
    override val rawId: String,
) : ScreenId {
    ContinueToProveYourIdentity(rawId = "f5d241ab-2835-42e6-b643-bb7bde897c18"),
}
