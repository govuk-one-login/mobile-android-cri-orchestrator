@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirmation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.idcheck.repositories.api.vendor.BiometricToken
import uk.gov.idcheck.repositories.api.webhandover.documenttype.DocumentType
import uk.gov.idcheck.repositories.api.webhandover.journeytype.JourneyType
import uk.gov.idcheck.sdk.IdCheckSdkParameters
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.IdCheckDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmPassportScreen(
    session: Session,
    viewModel: ConfirmPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.actions.collect {
            navController.navigate(
                IdCheckDestinations.SyncIdCheck(
                    IdCheckSdkParameters(
                        document = DocumentType.NFC_PASSPORT,
                        journey = JourneyType.MOBILE_APP_MOBILE,
                        sessionId = session.sessionId,
                        bioToken = BiometricToken(
                            accessToken = "Fake Access Token",
                            opaqueId = "Fake Opaque ID",
                        )
                    )
                ),
            )
        }
    }

    ConfirmPassportScreenContent(
        title = stringResource(ConfirmPassportConstants.titleId),
        confirmButtonText = stringResource(ConfirmPassportConstants.buttonTextId),
        onPrimaryClick = viewModel::onPrimary,
        modifier = modifier,
    )
}

@Composable
internal fun ConfirmPassportScreenContent(
    title: String,
    confirmButtonText: String,
    onPrimaryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    text = title,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.confirmdocument_passport_body_1),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.confirmdocument_passport_body_2),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Image(
                        painter = painterResource(R.drawable.nfc_passport),
                        contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                        modifier = Modifier.fillMaxWidth(),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = confirmButtonText,
                    buttonType = ButtonType.Primary,
                    onClick = onPrimaryClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmPassportScreen() {
    GdsTheme {
//        ConfirmPassportScreen()
    }
}
