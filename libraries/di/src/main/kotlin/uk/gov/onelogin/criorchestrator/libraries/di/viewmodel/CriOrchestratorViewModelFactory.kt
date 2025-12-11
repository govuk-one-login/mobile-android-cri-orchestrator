package uk.gov.onelogin.criorchestrator.libraries.di.viewmodel

import androidx.lifecycle.ViewModel
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metrox.viewmodel.ManualViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import kotlin.reflect.KClass

class CriOrchestratorViewModelFactory(
    override val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
    override val assistedFactoryProviders: Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>>,
    override val manualAssistedFactoryProviders:
        Map<KClass<out ManualViewModelAssistedFactory>, Provider<ManualViewModelAssistedFactory>>,
) : MetroViewModelFactory()

@BindingContainer
object ViewModelFactoryBindings {
    @Provides
    @SingleIn(CriOrchestratorScope::class)
    fun bindMetroViewModelFactory(
        viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>,
        assistedFactoryProviders: Map<KClass<out ViewModel>, Provider<ViewModelAssistedFactory>>,
        manualAssistedFactoryProviders:
            Map<KClass<out ManualViewModelAssistedFactory>, Provider<ManualViewModelAssistedFactory>>,
    ): MetroViewModelFactory =
        CriOrchestratorViewModelFactory(
            viewModelProviders,
            assistedFactoryProviders,
            manualAssistedFactoryProviders,
        )
}
