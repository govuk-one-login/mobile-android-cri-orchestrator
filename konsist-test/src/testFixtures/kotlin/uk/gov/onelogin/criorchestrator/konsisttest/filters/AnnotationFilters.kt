package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoAnnotationDeclaration
import com.lemonappdev.konsist.api.provider.KoAnnotationProvider

fun KoAnnotationDeclaration.isPreview(): Boolean = name == "Preview"

/**
 * Verifies that an annotated object contains at least two instances of the `@Preview` annotation, each handling
 * a different value for the `UI_MODE_NIGHT_YES` flag.
 *
 * @see hasUiModeNightEnabled
 */
fun KoAnnotationProvider.hasCustomLightDarkPreviewAnnotations(): Boolean =
    hasAnnotation { annotation ->
        annotation.isPreview() && annotation.hasArgument { it.hasUiModeNightEnabled() }
    } && hasAnnotation { annotation ->
        annotation.isPreview() && annotation.hasArgument { !it.hasUiModeNightEnabled() }
    }

fun <T : KoAnnotationProvider> Iterable<T>.withComposablePreviewAnnotations(): List<T> = filter {
    it.hasAnnotationWithName("Composable") &&
            it.hasAnnotation { annotation ->
                annotation.hasNameContaining("Preview")
            }
}
