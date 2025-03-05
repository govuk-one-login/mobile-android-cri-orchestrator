package uk.gov.onelogin.criorchestrator.features.resume.internal.modal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
internal fun rememberProveYourIdentityModalState(initiallyAllowedToShow: Boolean): ProveYourIdentityModalState =
    rememberSaveable(
        initiallyAllowedToShow,
        saver = proveYourIdentityModalStateSaver,
    ) {
        ProveYourIdentityModalState(
            allowedToShow = initiallyAllowedToShow,
        )
    }

@Stable
internal class ProveYourIdentityModalState(
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

internal val proveYourIdentityModalStateSaver =
    mapSaver(
        save = {
            mutableMapOf(
                "allowedToShow" to it.allowedToShow,
            )
        },
        restore = { saved ->
            ProveYourIdentityModalState(
                allowedToShow = saved["allowedToShow"] as Boolean,
            )
        },
    )
