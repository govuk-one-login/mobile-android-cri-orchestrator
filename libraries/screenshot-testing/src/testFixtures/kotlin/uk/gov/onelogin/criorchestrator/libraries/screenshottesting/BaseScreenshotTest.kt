package uk.gov.onelogin.criorchestrator.libraries.screenshottesting

import android.content.res.Configuration
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import app.cash.paparazzi.TestName
import com.android.resources.Density
import com.android.resources.NightMode
import org.junit.Rule
import org.junit.Test
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.android.device.DevicePreviewInfoParser
import sergio.sastre.composable.preview.scanner.android.screenshotid.AndroidPreviewScreenshotIdBuilder
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview
import kotlin.math.ceil

private const val BASELINE_DPI = 160f

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
        updateScreenshotName(paparazzi, preview)
        paparazzi.snapshot {
            ProvidesContentWithFakeViewModelStoreOwner {
                preview()
            }
        }
    }

    private fun createPaparazziRule(preview: ComposablePreview<AndroidPreviewInfo>): Paparazzi {
        val preview = preview.previewInfo
        val nightMode =
            when (preview.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
                true -> NightMode.NIGHT
                false -> NightMode.NOTNIGHT
            }
        val locale = preview.locale
        val parsedDevice =
            DevicePreviewInfoParser.parse(preview.device)?.inPx()
                ?: error("Invalid device ${preview.device}")
        val conversionFactor = parsedDevice.densityDpi / BASELINE_DPI
        val previewWidthInPx = ceil(preview.widthDp * conversionFactor).toInt()
        val previewHeightInPx = ceil(preview.heightDp * conversionFactor).toInt()
        return Paparazzi(
            deviceConfig =
                DeviceConfig(
                    density = Density(parsedDevice.densityDpi),
                    screenHeight =
                        when (preview.heightDp > 0) {
                            true -> previewHeightInPx
                            false -> parsedDevice.dimensions.height.toInt()
                        },
                    screenWidth =
                        when (preview.widthDp > 0) {
                            true -> previewWidthInPx
                            false -> parsedDevice.dimensions.width.toInt()
                        },
                    xdpi = parsedDevice.densityDpi, // not 100% precise
                    ydpi = parsedDevice.densityDpi,
                    nightMode = nightMode,
                    locale = locale.ifEmpty { null },
                ),
        )
    }

    private fun ComposablePreview<AndroidPreviewInfo>.screenshotId() =
        AndroidPreviewScreenshotIdBuilder(this)
            // Paparazzi screenshot names already include className and methodName
            .ignoreClassName()
            .ignoreMethodName()
            .build()

    /**
     * Set the screenshot name based on the preview under test rather than the test class.
     *
     * This is needed because there is only one screenshot test per module so the screenshot test
     * name is unnecessary noise in the screenshot names. It also ensures that when new screenshots
     * are added, screenshots from different packages do not need their names updating.
     */
    private fun updateScreenshotName(
        paparazzi: Paparazzi,
        preview: ComposablePreview<AndroidPreviewInfo>,
    ) {
        val packageName =
            preview.declaringClass
                .replace("uk.gov.onelogin.criorchestrator.", "")
                .split(".")
                .dropLast(1)
                .joinToString(".")
        val className = preview.declaringClass.split(".").last()
        val methodName = "${preview.methodName}_${preview.screenshotId()}"

        Paparazzi::class.java
            .getDeclaredField("testName")
            .apply {
                isAccessible = true
            }.set(
                paparazzi,
                TestName(
                    packageName = packageName,
                    className = className,
                    methodName = methodName,
                ),
            )
    }
}
