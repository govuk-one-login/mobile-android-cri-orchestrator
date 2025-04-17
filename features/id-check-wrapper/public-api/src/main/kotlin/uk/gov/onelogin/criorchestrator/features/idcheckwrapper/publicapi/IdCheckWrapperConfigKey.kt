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
}
