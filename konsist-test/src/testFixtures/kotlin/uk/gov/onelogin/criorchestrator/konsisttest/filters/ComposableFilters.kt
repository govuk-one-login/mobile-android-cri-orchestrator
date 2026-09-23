package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.provider.KoAnnotationProvider

fun <T : KoAnnotationProvider> List<T>.withComposablePreviewAnnotations(): List<T> = filter {
    it.hasAnnotationWithName("Composable") &&
        it.hasAnnotation { annotation ->
            annotation.hasNameContaining("Preview")
        }
}
