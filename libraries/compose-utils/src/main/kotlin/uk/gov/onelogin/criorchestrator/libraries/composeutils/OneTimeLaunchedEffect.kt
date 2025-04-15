package uk.gov.onelogin.criorchestrator.libraries.composeutils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope

/**
 * LaunchedEffect that only runs once and isn't re-run when recomposed.
 *
 * The coroutine will be cancelled when the [OneTimeLaunchedEffect] leaves
 * the composition.
 *
 * @param block The block to run once.
 */
@Composable
fun OneTimeLaunchedEffect(block: suspend CoroutineScope.() -> Unit) {
    var hasRunEffect by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!hasRunEffect) {
            block()
            hasRunEffect = true
        }
    }
}
