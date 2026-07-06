package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import uk.gov.idcheck.pages.errors.novalidsession.NoValidSessionError
import uk.gov.idcheck.pages.errors.novalidsession.NoValidSessionErrorParameters
import uk.gov.onelogin.idcheck.sdk.R

@Composable
fun NoValidSessionScreen(viewModel: NoValidSessionViewModel) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()
    }

    NoValidSessionError(generateParameters())
}

@Composable
private fun generateParameters(): NoValidSessionErrorParameters =
    NoValidSessionErrorParameters(
        title = stringResource(R.string.idsdk_no_valid_session_title),
        para1 = stringResource(R.string.idsdk_no_valid_session_para_1),
        para2 = stringResource(R.string.idsdk_no_valid_session_para_2),
        para3 = stringResource(R.string.idsdk_no_valid_session_para_3),
        footer = stringResource(R.string.idsdk_no_valid_session_footer),
        footerLink = stringResource(R.string.idsdk_no_valid_session_footer_link),
        isV1 = false,
    )
