package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.provider.KoPackageProvider

val libraryPackage = """uk\.gov\..*\.libraries\..*""".toRegex()
val featurePackageAny =
    """(uk\.gov\..*\.features\..*)\.(?:internal|internalapi|publicapi|)(?:\..*|$)""".toRegex()
val featurePackagePublicApi =
    """(uk\.gov\..*\.features\..*)\.publicapi(?:\..*|$)""".toRegex()
val featurePackageInternalApi =
    """(uk\.gov\..*\.features\..*)\.internalapi(?:\..*|$)""".toRegex()
val featurePackageInternal =
    """(uk\.gov\..*\.features\..*)\.internal(?:\..*|$)""".toRegex()
val featurePackageApi =
    """(uk\.gov\..*\.features\..*)\.(:?internalapi|publicapi)(?:\..*|$)""".toRegex()
val potentialFeaturePackage = "uk\\.gov\\..*\\.features\\..*".toRegex()

fun KoPackageProvider.hasPackageMatching(regex: Regex) = packagee?.hasNameMatching(regex) == true

fun <T : KoPackageProvider> Iterable<T>.withPackageMatching(regex: Regex): List<T> = filter {
    it.hasPackageMatching(regex)
}
