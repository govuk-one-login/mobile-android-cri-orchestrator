package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.withImport
import com.lemonappdev.konsist.api.verify.assertTrue
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import uk.gov.onelogin.criorchestrator.konsisttest.IdCheckSdkEncapsulationTest.Companion.idCheckSdkPackage
import uk.gov.onelogin.criorchestrator.konsisttest.IdCheckSdkEncapsulationTest.Companion.idCheckSdkWrapperInternalPackage
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

class IdCheckSdkEncapsulationTest {
    companion object {
        private val idCheckSdkPackage = """uk\.gov\.idcheck\..*""".toRegex()
        private val idCheckSdkWrapperInternalPackage =
            """uk\.gov\..*\.features\.idcheckwrapper.internal(\..*|$)""".toRegex()
        private val idCheckSdkWrapperInternalUiPackage =
            """uk\.gov\..*\.features\.idcheckwrapper.internalui(\..*|$)""".toRegex()
    }

    /**
     * Checks that [idCheckSdkPackage] and [idCheckSdkWrapperInternalPackage]
     * still reflect the package patterns for the ID Check SDK and this repository's
     * dedicated wrapper module.
     */
    @Test
    fun `id check sdk is referenced inside of the dedicated wrapper modules`() {
        val internalFilesImportingIdCheckSdk =
            Konsist
                .defaultScope()
                .files
                .withPackageMatching(idCheckSdkWrapperInternalPackage)
                .withImport {
                    it.hasNameMatching(idCheckSdkPackage)
                }

        val internalUiFilesImportingIdCheckSdk =
            Konsist
                .defaultScope()
                .files
                .withPackageMatching(idCheckSdkWrapperInternalPackage)
                .withImport {
                    it.hasNameMatching(idCheckSdkPackage)
                }

        Assertions.assertTrue(internalFilesImportingIdCheckSdk.isNotEmpty())
        Assertions.assertTrue(internalUiFilesImportingIdCheckSdk.isNotEmpty())
    }

    @Test
    fun `id check sdk is not referenced outside of the dedicated wrapper module`() {
        Konsist
            .defaultScope()
            .files
            .withImport {
                it.hasNameMatching(idCheckSdkPackage)
            }.assertTrue {
                it.hasPackageMatching(
                    idCheckSdkWrapperInternalPackage,
                ) ||
                    it.hasPackageMatching(
                        idCheckSdkWrapperInternalUiPackage,
                    )
            }
    }
}
