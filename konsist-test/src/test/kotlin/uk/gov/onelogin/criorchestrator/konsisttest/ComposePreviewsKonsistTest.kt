package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.provider.KoBodyProvider
import com.lemonappdev.konsist.api.verify.assertEmpty
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.composablePreviewsAreNotVisible
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.previewsHaveDarkAndLightMode
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class ComposePreviewsKonsistTest {

    @Test
    fun `Compose previews are not part of public API`() = defaultScope.composablePreviewsAreNotVisible()

    @Test
    fun `Compose previews have both light and dark versions`() = defaultScope.previewsHaveDarkAndLightMode(
        "PreviewLightDark",
        "LightDarkBothLocalesPreviews",
    )
}
