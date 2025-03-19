package uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.libraries.navigation.NavigationDestination

sealed interface ContinueToProveYourIdentityDestinations : NavigationDestination {
    @Serializable
    data object PassportJourney : ContinueToProveYourIdentityDestinations

    @Serializable
    data object DrivingLicenceJourney : ContinueToProveYourIdentityDestinations
}
