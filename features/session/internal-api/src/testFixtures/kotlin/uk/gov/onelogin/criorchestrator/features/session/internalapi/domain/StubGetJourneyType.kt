package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

class StubGetJourneyType(
    var journeyType: JourneyType = JourneyType.DesktopAppDesktop,
) : GetJourneyType {
    @Suppress("UNUSED")
    constructor(
        session: Session,
    ) : this(
        journeyType = session.journeyType,
    )

    override suspend fun invoke(): JourneyType = journeyType
}
