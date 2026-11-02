package uk.gov.onelogin.criorchestrator.features.session.internal.network.activesession

import kotlinx.serialization.Serializable

@Serializable
data class Flags(val disableBrpJourney: Boolean? = null)
