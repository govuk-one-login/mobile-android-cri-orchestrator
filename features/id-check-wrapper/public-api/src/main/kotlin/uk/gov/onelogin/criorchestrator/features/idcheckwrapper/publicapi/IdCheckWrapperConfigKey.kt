package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.publicapi

import uk.gov.onelogin.criorchestrator.features.config.publicapi.BooleanConfigKey

/**
 * Config keys for the ID Check wrapper.
 */
sealed interface IdCheckWrapperConfigKey {
    /**
     * Enable the manual ID Check SDK launcher.
     *
     * When enabled, the ID Check SDK won't launch automatically. Instead, the user can select a
     * a result from the launcher to test different user journeys.
     */
    data object EnableManualLauncher :
        BooleanConfigKey(
            name = "Enable manual ID Check SDK launcher",
        ),
        IdCheckWrapperConfigKey

    /**
     * Enable the experimental Compose navigation flow.
     *
     *  When enabled, the ID Check SDK will use Compose-based navigation instead of the current ID Check SDK
     *  XML-based navigation graph for part of the journey. This feature is part of ongoing migration efforts,
     *  and the flag will be removed in DCMAW-15387.
     */
    data object ExperimentalComposeNavigation :
        BooleanConfigKey(
            name = "Enable experimental Compose navigation",
        ),
        IdCheckWrapperConfigKey
}
