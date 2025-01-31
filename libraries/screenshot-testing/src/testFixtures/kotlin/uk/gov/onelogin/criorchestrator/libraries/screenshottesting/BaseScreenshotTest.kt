package uk.gov.onelogin.criorchestrator.libraries.screenshottesting

import android.content.res.Configuration
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import org.junit.Rule
import org.junit.Test
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.android.screenshotid.AndroidPreviewScreenshotIdBuilder
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview

/**
 * A base class to execute screenshot tests.
 *
 * Extend this class and use together with [PackagePreviewsProvider] to generate screenshot tests for
 * all the Composable previews in the project.
 *
 * #### Example usage
 *
 * ```kt
 * private object ModulePackagePreviewsProvider: PackagePreviewsProvider()
 *
 * @RunWith(TestParameterInjector::class)
 * class ScreenshotTest(
 *     @TestParameter(
 *         valuesProvider = ModulePackagePreviewsProvider::class,
 *     )
 *     preview: ComposablePreview<AndroidPreviewInfo>,
 * ) : BaseScreenshotTest(preview = preview)
 * ```
 *
 */
abstract class BaseScreenshotTest(
    val preview: ComposablePreview<AndroidPreviewInfo>,
) {
    @get:Rule
    val paparazzi: Paparazzi = createPaparazziRule(preview)

    @Test
    fun screenshot() {
        val name = preview.screenshotId()
        paparazzi.snapshot(name = name) {
            preview()
        }
    }

    private fun createPaparazziRule(preview: ComposablePreview<AndroidPreviewInfo>): Paparazzi {
        val nightMode =
            when (preview.previewInfo.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                true -> NightMode.NIGHT
                false -> NightMode.NOTNIGHT
            }
        return Paparazzi(
            deviceConfig =
                DeviceConfig(
                    nightMode = nightMode,
                ),
        )
    }

    private fun ComposablePreview<AndroidPreviewInfo>.screenshotId() =
        AndroidPreviewScreenshotIdBuilder(this)
            // Paparazzi screenshot names already include className and methodName
            .ignoreClassName()
            .ignoreMethodName()
            .build()
}
