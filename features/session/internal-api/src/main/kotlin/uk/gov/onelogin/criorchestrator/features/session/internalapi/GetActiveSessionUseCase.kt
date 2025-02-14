package uk.gov.onelogin.criorchestrator.features.session.internalapi

interface GetActiveSessionUseCase {
    suspend fun execute(): Boolean
}
