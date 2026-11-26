package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.verify.assertNotEmpty
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.IdCheckSdkEncapsulationTest.Companion.idCheckSdkPackage
import uk.gov.onelogin.criorchestrator.konsisttest.IdCheckSdkEncapsulationTest.Companion.idCheckSdkWrapperInternalPackage
import uk.gov.onelogin.criorchestrator.konsisttest.assertions.assertHasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withImportNamesMatching
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.filesWithinPackage

class IdCheckSdkEncapsulationTest {
    companion object {
        private val idCheckSdkPackage = """uk\.gov\.idcheck\..*""".toRegex()
        private val idCheckSdkWrapperInternalPackage =
            """uk\.gov\..*\.features\.idcheckwrapper.internal(\..*|$)""".toRegex()
    }

    /**
     * Checks that [idCheckSdkPackage] and [idCheckSdkWrapperInternalPackage]
     * still reflect the package patterns for the ID Check SDK and this repository's
     * dedicated wrapper module.
     */
    @Test
    fun `id check sdk is referenced inside of the dedicated wrapper module`() {
        defaultScope
            .filesWithinPackage(idCheckSdkWrapperInternalPackage)
            .withImportNamesMatching(idCheckSdkPackage)
            .assertNotEmpty()
    }

    @Test
    fun `id check sdk is not referenced outside of the dedicated wrapper module`() {
        defaultScope.files
            .withImportNamesMatching(idCheckSdkPackage)
            .assertHasPackageMatching(idCheckSdkWrapperInternalPackage)
    }
}
