package uk.gov.onelogin.criorchestrator.libraries.di.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provider
import dev.zacsweers.metro.Provides
import kotlin.reflect.KClass

@GraphExtension(CriOrchestratorViewModelScope::class)
interface ViewModelGraph {
    @Multibinds
    val viewModelProviders: Map<KClass<out ViewModel>, Provider<ViewModel>>

    @Provides
    fun provideSavedStateHandle(creationExtras: CreationExtras): SavedStateHandle =
        creationExtras.createSavedStateHandle()

    @GraphExtension.Factory
    fun interface Factory {
        fun createViewModelGraph(
            @Provides creationExtras: CreationExtras,
        ): ViewModelGraph
    }
}
