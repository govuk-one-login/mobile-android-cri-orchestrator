package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoArgumentDeclaration

@Suppress("ktlint:standard:function-expression-body")
fun KoArgumentDeclaration.hasUiModeNightEnabled(): Boolean {
    return name == "uiMode" && value!!.contains("UI_MODE_NIGHT_YES")
}
