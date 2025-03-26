package uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface SelectDocumentDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object Passport : SelectDocumentDestinations

    @Serializable
    data object Brp : SelectDocumentDestinations

    @Serializable
    data object DrivingLicence : SelectDocumentDestinations

    @Serializable
    data object TypesOfPhotoID : SelectDocumentDestinations

    @Serializable
    data object Confirm : SelectDocumentDestinations
}
