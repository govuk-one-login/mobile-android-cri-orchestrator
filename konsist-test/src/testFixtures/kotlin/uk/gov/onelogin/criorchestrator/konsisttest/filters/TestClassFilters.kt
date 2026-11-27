package uk.gov.onelogin.criorchestrator.konsisttest.filters

import com.lemonappdev.konsist.api.declaration.KoFileDeclaration
import com.lemonappdev.konsist.api.provider.KoAnnotationProvider
import com.lemonappdev.konsist.api.provider.KoClassAndObjectProvider
import com.lemonappdev.konsist.api.provider.KoImportProvider
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope

const val TEST_CLASS_NAME_SUFFIX = "Test"
const val SCREENSHOT_TEST_CLASS_NAME_SUFFIX = "ScreenshotTest"

val instrumentationTestFiles: List<KoFileDeclaration> by lazy {
    defaultScope.files
        .withAndroidTestSourceSet()
}

val androidUnitTestFiles: List<KoFileDeclaration> by lazy {
    defaultScope.files
        .withTestSourceSet()
        .withUiTestClass()
}

val moleculePaparazziUnitTestFiles: List<KoFileDeclaration> by lazy {
    defaultScope.files
        .withTestSourceSet()
        .withMoleculePaparazziCompatTests()
}

val unitTestFiles: List<KoFileDeclaration> by lazy {
    defaultScope.files
        .withTestSourceSet()
        .withUnitTestClass()
        .withoutMoleculePaparazziCompatTests()
}

val screenshotUnitTestFiles: List<KoFileDeclaration> by lazy {
    defaultScope.files
        .withTestSourceSet()
        .withScreenshotTestClass()
}

fun <T : KoClassAndObjectProvider> Iterable<T>.withUnitTestClass() = filter {
    it.hasClassOrObject { cls ->
        cls.hasNameEndingWith(TEST_CLASS_NAME_SUFFIX) &&
            !cls.hasNameEndingWith(SCREENSHOT_TEST_CLASS_NAME_SUFFIX) &&
            !cls.hasRunWithAndroidJUnitRunnerAnnotation()
    }
}

fun <T : KoClassAndObjectProvider> Iterable<T>.withUiTestClass() = filter {
    it.hasClassOrObject { cls ->
        cls.hasRunWithAndroidJUnitRunnerAnnotation()
    }
}

fun <T : KoClassAndObjectProvider> Iterable<T>.withScreenshotTestClass() = filter {
    it.hasClassOrObject { cls ->
        cls.hasNameEndingWith(SCREENSHOT_TEST_CLASS_NAME_SUFFIX)
    }
}

fun <T : KoImportProvider> Iterable<T>.withMoleculePaparazziCompatTests() = filter {
    it.hasMoleculePaparazziCompatRuleImport()
}

fun <T : KoImportProvider> Iterable<T>.withoutMoleculePaparazziCompatTests() = filter {
    !it.hasMoleculePaparazziCompatRuleImport()
}

fun KoImportProvider.hasMoleculePaparazziCompatRuleImport() = hasImport { import ->
    import.name == "uk.gov.onelogin.criorchestrator.libraries.screenshottesting.createMoleculePaparazziCompatRule"
}

private fun <T : KoAnnotationProvider> T.hasRunWithAndroidJUnitRunnerAnnotation(): Boolean = hasAnnotation {
    it.name == "RunWith" && it.hasArgument { it.value == "AndroidJUnit4::class" }
}
