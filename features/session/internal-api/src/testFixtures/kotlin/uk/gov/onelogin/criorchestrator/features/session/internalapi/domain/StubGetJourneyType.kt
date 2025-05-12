package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

class StubGetJourneyType(
    val journeyType: JourneyType = JourneyType.MobileAppMobile,
) : GetJourneyType {
    override suspend fun invoke(): JourneyType = journeyType
}
