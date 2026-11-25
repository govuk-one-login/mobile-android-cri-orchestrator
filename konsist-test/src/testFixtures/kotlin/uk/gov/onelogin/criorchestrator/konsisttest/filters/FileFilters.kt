package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.ext.list.withImport
import com.lemonappdev.konsist.api.provider.KoImportProvider

fun List<KoFileDeclaration>.withPackageMatching(regex: Regex) = filter {
    it.hasPackageMatching(regex)
}

fun KoFileDeclaration.hasPackageMatching(regex: Regex) = packagee?.hasNameMatching(regex) == true

fun <Importable : KoImportProvider> List<Importable>.withImportNamesMatching(
    regex: Regex
): List<Importable> = withImport {
    it.hasNameMatching(regex)
}
