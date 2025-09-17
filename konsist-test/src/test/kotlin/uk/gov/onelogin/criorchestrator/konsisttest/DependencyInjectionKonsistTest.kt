package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.classes
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.verify.assertEmpty
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.ContributesTo
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class DependencyInjectionKonsistTest {
    companion object {
        private val libraryPackage = """uk\.gov\..*\.libraries\..*""".toRegex()
    }

    @Test
    fun `library modules do not contribute graph bindings`() {
        Konsist
            .defaultScope()
            .files
            .withPackageMatching(libraryPackage)
            .classes()
            .withAnnotationOf(
                listOf(
                    ContributesTo::class,
                    ContributesBinding::class,
                    ContributesIntoSet::class,
                    ContributesIntoMap::class,
                ),
            ).assertEmpty(
                additionalMessage =
                    """
                    Libraries should be designed as simple standalone utilities and
                    shouldn't contribute dependencies directly to the SDK's dependency graph.
                    If you need to contribute an implementation from a library module into
                    the SDK, configure this in a feature module or sdk module.
                    Library modules may still declare binding containers or injectable classes.
                    """.trimIndent(),
            )
    }
}
