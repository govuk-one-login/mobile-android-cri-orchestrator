package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withComposablePreviewAnnotations
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class ComposePreviewsKonsistTest {
    @Test
    fun `Compose previews are not part of public API`() {
        Konsist
            .defaultScope()
            .functions()
            .withComposablePreviewAnnotations()
            .assertFalse {
                it.hasPublicOrDefaultModifier
            }
    }

    @Test
    fun `Compose previews have both light and dark versions`() {
        Konsist
            .defaultScope()
            .functions()
            .withComposablePreviewAnnotations()
            .assertTrue {
                it.hasAnnotationWithName("PreviewLightDark") || it.hasAnnotationWithName("LightDarkBothLocalesPreviews")
            }
    }
}
