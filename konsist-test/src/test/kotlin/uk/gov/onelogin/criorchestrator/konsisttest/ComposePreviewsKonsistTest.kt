package uk.gov.onelogin.criorchestrator.konsisttest

import org.junit.jupiter.api.Test

class ComposePreviewsKonsistTest {
    private val wrapper = GdsKonsistWrapper()

    @Test
    fun `Compose previews are not part of public API`() =
        wrapper.previewsAreNotVisible()

    @Test
    fun `Compose previews have both light and dark versions`() =
        wrapper.previewsHaveDarkAndLightMode(
            "PreviewLightDark",
            "LightDarkBothLocalesPreviews"
        )
}
