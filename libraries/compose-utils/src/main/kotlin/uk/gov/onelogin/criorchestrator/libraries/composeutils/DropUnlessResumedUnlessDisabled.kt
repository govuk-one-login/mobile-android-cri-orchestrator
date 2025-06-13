package uk.gov.onelogin.criorchestrator.libraries.composeutils

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.compose.dropUnlessResumed
import org.jetbrains.annotations.VisibleForTesting

/**
 * Applies [dropUnlessResumed] if [LocalDropUnlessResumedDisabled] is `false`.
 *
 * This is an unfortunate workaround for the fact that it's not currently possible to
 * determine whether a composable is in the resumed state before clicking it in UI tests.
 *
 * To disable the [dropUnlessResumed] behaviour, set [LocalDropUnlessResumedDisabled]
 * to `true` in your test.
 */
@Composable
fun dropUnlessResumedUnlessDisabledForTesting(block: () -> Unit) =
    if (LocalDropUnlessResumedDisabled.current) {
        { block() }
    } else {
        dropUnlessResumed { block() }
    }

/**
 * Whether to disable the [dropUnlessResumed] behaviour.
 */
@SuppressLint("ComposeCompositionLocalUsage")
@VisibleForTesting
val LocalDropUnlessResumedDisabled = staticCompositionLocalOf { false }
