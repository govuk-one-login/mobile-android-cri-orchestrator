package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import android.content.res.Configuration
import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.handback.internal.R

@Composable
fun NoValidSessionScreen(onScreenStart: () -> Unit) {
    LaunchedEffect(Unit) {
        onScreenStart()
    }

    NoValidSessionError(generateParameters())
}

@Composable
private fun generateParameters(): NoValidSessionErrorParameters =
    NoValidSessionErrorParameters(
        title = stringResource(R.string.handback_no_valid_session_title),
        para1 = stringResource(R.string.handback_no_valid_session_para_1),
        para2 = stringResource(R.string.handback_no_valid_session_para_2),
        para3 = stringResource(R.string.handback_no_valid_session_para_3),
        isV1 = false,
    )

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
internal fun ErrorPagePreview(
    @PreviewParameter(NoValidSessionErrorProvider::class)
    parameters: NoValidSessionErrorParameters,
) {
    GdsTheme {
        NoValidSessionError(parameters)
    }
}
