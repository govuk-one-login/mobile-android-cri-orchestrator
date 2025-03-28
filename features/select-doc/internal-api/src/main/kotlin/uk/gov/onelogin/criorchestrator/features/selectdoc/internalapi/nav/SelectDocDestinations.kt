package uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav

import kotlinx.serialization.Serializable
import uk.gov.onelogin.criorchestrator.features.resume.internalapi.nav.ProveYourIdentityDestinations

sealed interface SelectDocDestinations : ProveYourIdentityDestinations {
    @Serializable
    data object Passport : SelectDocDestinations

    @Serializable
    data object Brp : SelectDocDestinations

    @Serializable
    data object DrivingLicence : SelectDocDestinations

    @Serializable
    data object TypesOfPhotoID : SelectDocDestinations

    @Serializable
    data object ConfirmPassport : SelectDocDestinations

    @Serializable
    data object ConfirmBrp : SelectDocDestinations

    @Serializable
    data object ConfirmDrivingLicence : SelectDocDestinations
}
