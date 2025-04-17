package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.biometrictoken

import uk.gov.idcheck.repositories.api.vendor.BiometricToken

fun BiometricToken.Companion.createTestToken(
    accessToken: String = "SlAV32hkKG",
    opaqueId: String = "11111111-1111-1111-1111-111111111111",
): BiometricToken =
    BiometricToken(
        accessToken = accessToken,
        opaqueId = opaqueId,
    )
