package uk.gov.onelogin.criorchestrator.testwrapper

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.runner.RunWith
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview
import uk.gov.onelogin.criorchestrator.libraries.screenshottesting.BaseScreenshotTest
import uk.gov.onelogin.criorchestrator.libraries.screenshottesting.PackagePreviewsProvider

private object ModulePackagePreviewsProvider : PackagePreviewsProvider()

@RunWith(TestParameterInjector::class)
class ScreenshotTest(
    @TestParameter(
        valuesProvider = ModulePackagePreviewsProvider::class,
    )
    preview: ComposablePreview<AndroidPreviewInfo>,
) : BaseScreenshotTest(preview = preview)
