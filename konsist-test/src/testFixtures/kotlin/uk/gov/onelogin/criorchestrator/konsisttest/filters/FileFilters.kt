package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration

fun KoFileDeclaration.packagesDoNotContain(packagesToSearch: Regex, disallowedPackages: Regex): Boolean {
    val currentPackage = packagesToSearch.find(packagee!!.text)!!.groupValues[1]
    return imports.filter { import ->
        !import.hasNameStartingWith(currentPackage)
    }.none { import ->
        import.hasNameMatching(disallowedPackages)
    }
}
