package uk.gov.onelogin.criorchestrator.libraries.composeutils

import androidx.compose.ui.window.DialogProperties

val fullScreenDialogProperties =
    DialogProperties(
        usePlatformDefaultWidth = false,
        // Dialog destinations don't allow us to intercept this
        // dismiss request and it shouldn't be possible from a
        // full screen dialog anyway, so disable it.
        dismissOnClickOutside = false,
    )
