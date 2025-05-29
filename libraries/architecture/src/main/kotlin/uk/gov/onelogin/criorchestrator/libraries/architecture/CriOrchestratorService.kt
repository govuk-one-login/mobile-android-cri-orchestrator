package uk.gov.onelogin.criorchestrator.libraries.architecture

/**
 * Service that runs continuously for the lifetime of a component.
 */
fun interface CriOrchestratorService {
    suspend fun start()
}
