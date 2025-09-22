package uk.gov.onelogin.criorchestrator.libraries.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.Inject
import kotlin.reflect.KClass

/**
 * A [ViewModelProvider.Factory] that uses an injected map of [KClass] to [ViewModel] providers.
 *
 * Use [ViewModelKey] to contribute view models to this factory.
 */
@Inject
class ViewModelProviderFactory(
    private val graphFactory: ViewModelGraph.Factory,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras,
    ): T {
        val provider =
            graphFactory
                .createViewModelGraph(creationExtras = extras)
                .viewModelProviders[modelClass]
                ?: throw IllegalArgumentException("Unknown view model class $modelClass")

        return provider() as T
    }
}
