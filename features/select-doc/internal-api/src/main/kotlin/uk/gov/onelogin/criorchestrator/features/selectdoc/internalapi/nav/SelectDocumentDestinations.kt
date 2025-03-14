package uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

sealed interface SelectDocumentDestinations : NavigationDestination {
    @Serializable
    data object SelectDocument : SelectDocumentDestinations
}
