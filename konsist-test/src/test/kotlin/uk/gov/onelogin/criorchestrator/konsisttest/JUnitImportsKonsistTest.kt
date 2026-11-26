package uk.gov.onelogin.criorchestrator.konsisttest

import com.google.testing.junit.testparameterinjector.junit5.KotlinTestParameters.namedTestValuesIn
import com.google.testing.junit.testparameterinjector.junit5.TestParameter
import com.google.testing.junit.testparameterinjector.junit5.TestParameterInjectorTest
import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import uk.gov.onelogin.criorchestrator.konsisttest.filters.androidUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit4
import uk.gov.onelogin.criorchestrator.konsisttest.filters.doesNotImportJUnit5
import uk.gov.onelogin.criorchestrator.konsisttest.filters.instrumentationTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.moleculePaparazziUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.screenshotUnitTestFiles
import uk.gov.onelogin.criorchestrator.konsisttest.filters.unitTestFiles

class JUnitImportsKonsistTest {
    @TestParameterInjectorTest
    fun certainTestTypesDoNotUseJUnit4(
        @TestParameter fileFilter: List<KoFileDeclaration> = namedTestValuesIn(junit5FileFilters),
    ) = fileFilter.assertTrue { it.doesNotImportJUnit4() }

    @TestParameterInjectorTest
    fun certainTestTypesDoNotUseJUnit5(
        @TestParameter fileFilter: List<KoFileDeclaration> = namedTestValuesIn(junit4FileFilters),
    ) = fileFilter.assertTrue { it.doesNotImportJUnit5() }

    private companion object {
        val junit4FileFilters = mapOf(
            "Local UI tests" to androidUnitTestFiles,
            "Screenshot tests" to screenshotUnitTestFiles,
            "Instrumentation tests" to instrumentationTestFiles,
            // https://github.com/cashapp/paparazzi/issues/1149
            "Molecule Paparazzi compat. tests" to moleculePaparazziUnitTestFiles,
        )

        val junit5FileFilters = mapOf(
            "Unit tests" to unitTestFiles,
        )
    }
}
