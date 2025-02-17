package uk.gov.onelogin.criorchestrator.features.session.internalapi

fun interface GetActiveSessionUseCase {
    suspend fun execute(): Boolean
}
