package uk.gov.onelogin.criorchestrator.testwrapper.devmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
internal fun rememberDevMenuState(initiallyAllowedToShow: Boolean): DevMenuState =
    rememberSaveable(
        initiallyAllowedToShow,
        saver = devMenuStateSaver,
    ) {
        DevMenuState(
            allowedToShow = initiallyAllowedToShow,
        )
    }

@Stable
internal class DevMenuState(
    allowedToShow: Boolean,
) {
    var allowedToShow by mutableStateOf(allowedToShow)
        private set

    fun allowToShow() {
        allowedToShow = true
    }

    fun onDismissRequest() {
        allowedToShow = false
    }
}

internal val devMenuStateSaver =
    mapSaver(
        save = {
            mutableMapOf(
                "allowedToShow" to it.allowedToShow,
            )
        },
        restore = { saved ->
            DevMenuState(
                allowedToShow = saved["allowedToShow"] as Boolean,
            )
        },
    )
