package uk.gov.onelogin.criorchestrator.features.resume.internal.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenBodyContent
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreenButton
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.onelogin.criorchestrator.features.resume.internal.R

@Composable
internal fun ContinueToProveYourIdentityScreen(modifier: Modifier = Modifier) =
    CentreAlignedScreen(
        title = stringResource(R.string.continue_to_prove_your_identity_screen_title),
        body =
            persistentListOf(
                CentreAlignedScreenBodyContent.Text(
                    stringResource(R.string.continue_to_prove_your_identity_screen_body),
                ),
            ),
        modifier = modifier.fillMaxSize(),
        primaryButton =
            CentreAlignedScreenButton(
                text = stringResource(R.string.continue_to_prove_your_identity_screen_button),
                onClick = {
                    // TODO
                },
            ),
    )

@PreviewLightDark
@Preview(locale = "cy")
@Composable
internal fun ContinueToProveYourIdentityScreenPreview() =
    GdsTheme {
        ContinueToProveYourIdentityScreen()
    }
