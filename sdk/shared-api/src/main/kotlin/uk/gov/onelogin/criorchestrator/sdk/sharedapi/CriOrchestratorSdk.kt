package uk.gov.onelogin.criorchestrator.sdk.sharedapi

/**
 * Interface for the SDK.
 *
 * Take care to ensure that only one instance of this object is created.
 */
interface CriOrchestratorSdk {
    /**
     * Holds the dependency graph. For internal use.
     */
    val appGraph: CriOrchestratorAppGraph

    companion object

    // Functions that provide access to the SDK components can go here
}
