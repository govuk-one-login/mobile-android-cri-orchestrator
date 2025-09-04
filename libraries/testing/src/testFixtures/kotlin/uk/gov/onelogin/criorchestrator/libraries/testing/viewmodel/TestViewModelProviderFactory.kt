package uk.gov.onelogin.criorchestrator.libraries.testing.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class TestViewModelProviderFactory(
    vararg viewModelProviders: TestViewModelProvider,
) : ViewModelProvider.Factory {
    private val viewModelProviders =
        viewModelProviders.associate {
            it.kClass to it.provider
        }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: KClass<T>,
        extras: CreationExtras,
    ): T =
        (viewModelProviders[modelClass] as? () -> T)?.invoke()
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
}

inline fun <reified T : ViewModel> testViewModelProvider(noinline provider: () -> T): TestViewModelProvider =
    TestViewModelProvider(
        kClass = T::class,
        provider = provider,
    )

data class TestViewModelProvider(
    val kClass: KClass<out ViewModel>,
    val provider: () -> ViewModel,
)
