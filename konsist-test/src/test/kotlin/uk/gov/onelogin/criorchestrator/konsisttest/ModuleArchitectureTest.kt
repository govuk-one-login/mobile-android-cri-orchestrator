package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.Konsist.scopeFromTest
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class ModuleArchitectureTest {
    companion object {
        private val libraryPackage = """uk\.gov\..*\.libraries\..*""".toRegex()
        private val featurePackageAny =
            """(uk\.gov\..*\.features\..*)\.(?:internal|internalapi|publicapi)(?:\..*|$)""".toRegex()
        private val featurePackageInternal =
            """(uk\.gov\..*\.features\..*)\.internal(?:\..*|$)""".toRegex()
        private val featurePackageApi =
            """(uk\.gov\..*\.features\..*)\.(:?internalapi|publicapi)(?:\..*|$)""".toRegex()
    }

    @Test
    fun `feature modules have valid package names`() {
        val potentialFeaturePackage =
            "uk\\.gov\\..*\\.features\\..*".toRegex()
        Konsist
            .defaultScope()
            .files
            .withPackageMatching(potentialFeaturePackage)
            .assertTrue {
                it.hasPackageMatching(featurePackageAny)
            }
    }

    @Test
    fun `feature modules do not depend directly on other features internals`() {
        Konsist
            .defaultScope()
            .files
            .withPackageMatching(featurePackageAny)
            .assertTrue(
                additionalMessage =
                    """
                    Features shouldn't know about other features internals.
                    Features should depend on each other indirectly, through API modules.
                    Implementations should be injected.
                    If a test fixture is needed across different features, then it is part of the
                    API of the feature and the implementation should live in one of its feature's
                    API modules.
                    """.trimIndent(),
            ) {
                val thisFeaturePackage =
                    featurePackageAny.find(it.packagee!!.text)!!.groupValues[1]
                it.imports
                    .filter {
                        !it.hasNameStartingWith(thisFeaturePackage)
                    }.none {
                        it.hasNameMatching(featurePackageInternal)
                    }
            }
    }

    @Test
    fun `api modules do not contain implementations`() {
        Konsist
            .defaultScope()
            .minus(scope = scopeFromTest())
            .files
            .withPackageMatching(featurePackageApi)
            .assertTrue(
                additionalMessage =
                    """
                    Implementations should live in a feature's internal module.
                    Only interfaces data structures should be exposed through API modules.
                    """.trimIndent(),
            ) {
                it.classes().all {
                    it.hasAbstractModifier ||
                        it.hasDataModifier ||
                        it.hasEnumModifier
                }
            }
    }

    @Test
    fun `library modules do not depend on feature modules`() {
        Konsist
            .defaultScope()
            .files
            .withPackageMatching(libraryPackage)
            .assertTrue {
                it.imports.none {
                    it.hasNameMatching(featurePackageAny)
                }
            }
    }
}
