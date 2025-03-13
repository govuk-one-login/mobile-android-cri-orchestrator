package uk.gov.onelogin.criorchestrator.features.documentselection.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

sealed interface DocumentSelectionDestinations : NavigationDestination {
    @Serializable
    data object DocumentSelection : DocumentSelectionDestinations
}
