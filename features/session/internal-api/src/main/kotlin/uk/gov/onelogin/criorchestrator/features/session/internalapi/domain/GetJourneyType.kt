package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

fun interface GetJourneyType {
    suspend operator fun invoke(): JourneyType
}
