package uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

sealed interface ProveYourIdentityDestinations : NavigationDestination {
    @Serializable
    data object ContinueToProveYourIdentity : ProveYourIdentityDestinations
}
