package uk.gov.onelogin.criorchestrator.konsisttest

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.container.KoScope
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.ext.list.classes
import com.lemonappdev.konsist.api.ext.list.withAnnotationOf
import com.lemonappdev.konsist.api.provider.KoAnnotationProvider
import com.lemonappdev.konsist.api.verify.assertEmpty
import com.lemonappdev.konsist.api.verify.assertFalse
import com.lemonappdev.konsist.api.verify.assertTrue
import uk.gov.onelogin.criorchestrator.konsisttest.filters.hasCustomLightDarkPreviewAnnotations
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withComposablePreviewAnnotations
import uk.gov.onelogin.criorchestrator.konsisttest.filters.withPackageMatching
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.classesWithinRoot
import uk.gov.onelogin.criorchestrator.konsisttest.scopes.defaultScope
import java.util.Collections.singleton
import kotlin.reflect.KClass

/**
 * Wrapper object for containing functions / standards enforced through `konsist`.
 *
 * @param scope The file scope to examine. Defaults to [defaultScope].
 */
class GdsKonsistWrapper(
    private val scope: KoScope = Konsist.defaultScope()
) {
    /**
     * Verifies that all Jetpack Compose `Preview` functions have a visibility that isn't public or
     * default.
     */
    fun previewsAreNotVisible() = onComposablePreviewAnnotations()
        .assertFalse(
            additionalMessage =
                """
                Compose preview functions shouldn't be considered part of the public-facing API
                within a given module, as they aren't useful to consuming projects. 
                """.trimIndent(),
            testName = "Composable previews aren't visible"
        ) { function ->
            function.hasPublicOrDefaultModifier
        }

    /**
     * Verifies that all Jetpack Compose `Preview` functions contain both a dark and light mode
     * preview.
     *
     * Initially checks [previewAnnotationNames] for associated previews that provide this
     * information. Otherwise, `konsist` checks the `uiMode` property for `UI_MODE_NIGHT_YES`.
     *
     * @param previewAnnotationNames The custom annotations handling dark/light mode that the code
     * base uses. Defaults to `["PreviewLightDark"]` from the android tooling dependency's
     * `MultiPreviews` file.
     * @see hasCustomLightDarkPreviewAnnotations
     */
    fun previewsHaveDarkAndLightMode(
        previewAnnotationNames: Iterable<String> = listOf(
            "PreviewLightDark"
        )
    ) = onComposablePreviewAnnotations()
        .assertTrue(
            additionalMessage =
                """
                Compose preview functions should contain variants that visualise differences
                between dark and light UI modes.
                """.trimIndent(),
            testName = "Composable previews have dark/light mode variants"
        ) { function ->
            previewAnnotationNames
                .map(function::hasAnnotationWithName)
                .fold(false, Boolean::or) ||
                    function.hasCustomLightDarkPreviewAnnotations()
        }

    /**
     * Verifies that all Jetpack Compose `Preview` functions contain both a dark and light mode
     * preview.
     *
     * Initially checks [previewAnnotationNames] for associated previews that provide this
     * information. Otherwise, `konsist` checks the `uiMode` property for `UI_MODE_NIGHT_YES`.
     *
     * @param previewAnnotationNames The custom annotations handling dark/light mode that the code
     * base uses.
     * @see hasCustomLightDarkPreviewAnnotations
     */
    fun previewsHaveDarkAndLightMode(
        previewAnnotationName: String,
        vararg previewAnnotationNames: String
    ) = previewsHaveDarkAndLightMode(
        singleton(previewAnnotationName) + previewAnnotationNames.toList()
    )

    /**
     *
     */
    fun classesDoNotUseProvidedAnnotations(
        packageRegex: Regex,
        annotations: Collection<KClass<*>>,
        additionalMessage: String? = null,
        testName: String? = null,
    ) = doesNotUseProvidedAnnotations(
        annotations = annotations,
        additionalMessage = additionalMessage,
        testName = testName,
    ) { classesWithinRoot(packageRegex) }

    /**
     * Verifies that there's no [annotations] attached on the return value of [filter].
     *
     * @param annotations The [Collection] of annotation classes that shouldn't be on the
     * [filter]ed results.
     * @param additionalMessage The additional message for the assertion, such as the reason
     * for enforcing the `konsist` rule. Defaults to `null`, meaning that there's no additional
     * message.
     * @param testName The name of the test for `kotest` purposes. Defaults to `null`, meaning
     * that test names are automatically generated from the test function's context.
     * @param filter Converts the [GdsKonsistWrapper] instance into a List of annotatable
     * objects.
     *
     * @see assertEmpty
     * @sample classesDoNotUseProvidedAnnotations
     */
    fun doesNotUseProvidedAnnotations(
        annotations: Collection<KClass<*>>,
        additionalMessage: String? = null,
        testName: String? = null,
        filter: KoScope.() -> List<KoAnnotationProvider>
    ) = filter(scope)
        .withAnnotationOf(annotations)
        .assertEmpty(
            testName = testName,
            additionalMessage = additionalMessage,
        )

    /**
     * @return a List of kotlin functions that have the `@Preview` annotation.
     */
    fun onComposablePreviewAnnotations() = scope
        .functions()
        .withComposablePreviewAnnotations()
}