package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertAllClassesMatchAtLeastOneOf
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertDoesNotImport
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertHasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertPackagesDoNotContain
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageAny
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageApi
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageInternal
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackageInternalApi
import uk.gov.onelogin.criorchestrator.konsisttest.filters.featurePackagePublicApi
import uk.gov.onelogin.criorchestrator.konsisttest.filters.libraryPackage
import uk.gov.onelogin.criorchestrator.konsisttest.filters.potentialFeaturePackage
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.filesWithinPackage
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.productionCodeScope

class ModuleArchitectureTest {

    @Test
    fun `feature modules have valid package names`() {
        defaultScope.filesWithinPackage(potentialFeaturePackage)
            .assertHasPackageMatching(featurePackageAny)
    }

    @Test
    fun `feature modules do not depend directly on other features internals`() {
        defaultScope.filesWithinPackage(featurePackageAny)
            .assertPackagesDoNotContain(
                additionalMessage =
                    """
                    Features shouldn't know about other features internals.
                    Features should depend on each other indirectly, through API modules.
                    Implementations should be injected.
                    If a test fixture is needed across different features, then it is part of the
                    API of the feature and the implementation should live in one of its feature's
                    API modules.
                    """.trimIndent(),
                packagesToSearch = featurePackageAny,
                disallowedPackage = featurePackageInternal,
            )
    }

    @Test
    fun `public api modules do not depend on internal api or internal implementation modules`() {
        defaultScope.filesWithinPackage(featurePackagePublicApi)
            .assertDoesNotImport(
                additionalMessage =
                    """
                    Public API modules shouldn't know about internals.
                    Internal API modules and internal implementation modules may know about public APIs but not 
                    the other way around.
                    """.trimIndent(),
                disallowedPackages = listOf(featurePackageInternal, featurePackageInternalApi),
            )
    }

    @Test
    fun `internal api modules do not depend on internal implementation modules`() {
        defaultScope.filesWithinPackage(featurePackageInternalApi)
            .assertDoesNotImport(
                additionalMessage =
                    """
                    Internal API modules shouldn't know about internal implementations.
                    Internal implementation modules may know about the internal APIs but not the other way around.
                    """.trimIndent(),
                disallowedPackage = featurePackageInternal,
            )
    }

    @Test
    fun `api modules do not contain implementations`() {
        productionCodeScope
            .filesWithinPackage(featurePackageApi)
            .assertAllClassesMatchAtLeastOneOf(
                additionalMessage =
                    """
                    Implementations should live in a feature's internal module.
                    Only interfaces data structures should be exposed through API modules.
                    """.trimIndent(),
                validations = listOf(
                    KoClassDeclaration::hasAbstractModifier,
                    KoClassDeclaration::hasDataModifier,
                    KoClassDeclaration::hasEnumModifier,
                ),
            )
    }

    @Test
    fun `library modules do not depend on feature modules`() {
        defaultScope.filesWithinPackage(libraryPackage)
            .assertDoesNotImport(featurePackageAny)
    }
}
