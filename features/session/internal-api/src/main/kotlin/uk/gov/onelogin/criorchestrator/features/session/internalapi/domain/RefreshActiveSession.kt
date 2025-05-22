package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

fun interface RefreshActiveSession {
    suspend operator fun invoke()
}
