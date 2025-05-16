package uk.gov.onelogin.criorchestrator.features.dev.internal.screen

import kotlinx.collections.immutable.ImmutableList
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config

data class DevMenuUiState(
    val entries: ImmutableList<Config.Entry<Config.Value>>,
)
