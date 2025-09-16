@file:OptIn(UnstableDesignSystemAPI::class)

package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport.confirm

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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import uk.gov.android.ui.componentsv2.button.ButtonTypeV2
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalapi.nav.IdCheckWrapperDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmPassportScreen(
    viewModel: ConfirmPassportViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.action.collect { event ->
            navController.navigate(
                IdCheckWrapperDestinations.SyncIdCheckScreen(
                    event.documentVariety,
                ),
            )
        }
    }

    ConfirmPassportScreenContent(
        title = stringResource(ConfirmPassportConstants.titleId),
        confirmButtonText = stringResource(ConfirmPassportConstants.buttonTextId),
        onPrimaryClick = viewModel::onConfirmClick,
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
                    textAlign = GdsHeadingAlignment.LeftAligned,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    Text(
                        text = stringResource(R.string.confirmdocument_passport_body_1),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.confirmdocument_passport_body_2),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
                item {
                    Image(
                        painter = painterResource(R.drawable.nfc_passport),
                        contentDescription = stringResource(R.string.selectdocument_passport_imagedescription),
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    text = confirmButtonText,
                    buttonType = ButtonTypeV2.Primary(),
                    onClick = dropUnlessResumed { onPrimaryClick() },
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
        ConfirmPassportScreenContent(
            title = stringResource(ConfirmPassportConstants.titleId),
            confirmButtonText = stringResource(ConfirmPassportConstants.buttonTextId),
            onPrimaryClick = { },
        )
    }
}
