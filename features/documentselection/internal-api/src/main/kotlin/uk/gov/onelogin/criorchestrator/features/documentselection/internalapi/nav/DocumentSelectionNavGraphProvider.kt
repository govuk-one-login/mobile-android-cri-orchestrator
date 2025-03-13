package uk.gov.onelogin.criorchestrator.features.documentselection.internalapi.nav

import uk.gov.onelogin.criorchestrator.libraries.navigation.NavGraphProvider

/**
 * Interface which [NavGraphProvider]s must implement to contribute to the navigation graph
 * started by the 'prove your identity' feature.
 */
interface DocumentSelectionNavGraphProvider : NavGraphProvider
