import com.android.build.api.dsl.LibraryExtension
import uk.gov.onelogin.criorchestrator.extensions.setNamespace
import uk.gov.pipelines.extensions.BaseExtensions.baseAndroidConfig

plugins {
    alias(libs.plugins.android.library)
    id("uk.gov.pipelines.jvm-toolchains")
    id("uk.gov.onelogin.criorchestrator.unit-test-config")
    id("uk.gov.onelogin.criorchestrator.code-quality-config")
}

configure<LibraryExtension> {
    baseAndroidConfig(project)
    setNamespace(project = project)

    packaging {
        /*
         * Clash between:
         * - kotlin-compiler-embeddable:2.4.21/kotlin-compiler-embeddable-2.4.21.jar
         * - kotlin-stdlib:2.4.21/kotlin-stdlib-2.4.21.jar
         */
        resources.pickFirsts += listOf(
            "kotlin/internal/internal.kotlin_builtins",
            "kotlin/reflect/reflect.kotlin_builtins"
        )
    }
}

// https://docs.konsist.lemonappdev.com/advanced/isolate-konsist-tests
tasks.withType<Test> {
    outputs.upToDateWhen { false }
}

dependencies {
    testFixturesApi(libs.com.lemonappdev.konsist)
    listOf(
        libs.google.testparameterinjector.junit5,
        libs.metro.runtime,
    ).forEach(::testImplementation)
}
