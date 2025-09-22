package uk.gov.onelogin.criorchestrator.libraries.di.viewmodel

import androidx.lifecycle.ViewModelProvider
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.Binds

@BindingContainer
fun interface ViewModelProviderFactoryBindings {
    @Binds
    fun viewModelProviderFactory(impl: ViewModelProviderFactory): ViewModelProvider.Factory
}
