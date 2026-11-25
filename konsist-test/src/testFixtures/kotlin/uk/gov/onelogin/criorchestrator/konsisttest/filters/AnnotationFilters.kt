package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoArgumentDeclaration
import com.lemonappdev.konsist.api.provider.KoAnnotationProvider

fun KoAnnotationProvider.hasCustomLightDarkPreviewAnnotations() = hasAnnotation { annotation ->
    annotation.name == "Preview" && annotation.hasArgument { it.hasUiModeNightEnabled() }
} && hasAnnotation { annotation ->
    annotation.name == "Preview" && annotation.hasArgument { !it.hasUiModeNightEnabled() }
}

@Suppress("ktlint:standard:function-expression-body")
fun KoArgumentDeclaration.hasUiModeNightEnabled(): Boolean {
    return name == "uiMode" && value!!.contains("UI_MODE_NIGHT_YES")
}
