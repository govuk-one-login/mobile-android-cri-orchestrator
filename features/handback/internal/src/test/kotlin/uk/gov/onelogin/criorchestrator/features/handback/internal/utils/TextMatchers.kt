package uk.gov.onelogin.criorchestrator.features.handback.internal.utils

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher

fun hasTextStartingWith(prefix: String): SemanticsMatcher =
    SemanticsMatcher("Text starts with: $prefix") {
        val text =
            it.config
                .getOrNull(SemanticsProperties.Text)
                ?.firstOrNull()
                ?.text
        text?.startsWith(prefix) == true
    }
