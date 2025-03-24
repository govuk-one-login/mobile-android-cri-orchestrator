package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration

fun List<KoFileDeclaration>.withPackageMatching(regex: Regex) =
    filter {
        it.hasPackageMatching(regex)
    }

fun KoFileDeclaration.hasPackageMatching(regex: Regex) = packagee?.hasNameMatching(regex) == true
