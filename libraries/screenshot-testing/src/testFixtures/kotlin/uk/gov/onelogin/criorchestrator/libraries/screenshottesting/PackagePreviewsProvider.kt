package uk.gov.onelogin.criorchestrator.libraries.screenshottesting

import com.google.testing.junit.testparameterinjector.TestParameterValuesProvider
import sergio.sastre.composable.preview.scanner.android.AndroidComposablePreviewScanner
import sergio.sastre.composable.preview.scanner.android.AndroidPreviewInfo
import sergio.sastre.composable.preview.scanner.core.preview.ComposablePreview

/**
 * A [TestParameterValuesProvider] that provides all the composable previews found with the same
 * package as the instance of this class.
 *
 * To create a provider for a given module, create a new subclass in the module.
 *
 * ```kt
 * object ModulePackagePreviewsProvider: PackagePreviewsProvider()
 * ```
 */
abstract class PackagePreviewsProvider : TestParameterValuesProvider() {
    private val namespace = this::class.java.`package`!!.name

    override fun provideValues(context: Context?): List<ComposablePreview<AndroidPreviewInfo>> =
        AndroidComposablePreviewScanner()
            .scanPackageTrees(namespace)
            .getPreviews()
}
