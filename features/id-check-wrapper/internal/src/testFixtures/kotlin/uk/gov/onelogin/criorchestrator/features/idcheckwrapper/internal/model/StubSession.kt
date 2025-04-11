package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.createTestInstance

fun Session.Companion.createDesktopAppDesktopInstance(): Session =
    Session.createTestInstance(
        redirectUri = null,
    )

fun Session.Companion.createMobileAppMobileInstance(): Session =
    Session.createTestInstance(
        redirectUri = "http://mam-redirect-uri",
    )
