package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.confirmnoid.nochippedid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.bulletedlist.BulletedListTitle
import uk.gov.android.ui.componentsv2.bulletedlist.GdsBulletedList
import uk.gov.android.ui.componentsv2.bulletedlist.TitleType
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
import uk.gov.onelogin.criorchestrator.features.selectdoc.internalapi.nav.SelectDocDestinations
import uk.gov.onelogin.criorchestrator.libraries.composeutils.LightDarkBothLocalesPreview

@Composable
internal fun ConfirmNoChippedIDScreen(
    viewModel: ConfirmNoChippedIDViewModel,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        viewModel.onScreenStart()

        viewModel.action.collect { event ->
            when (event) {
                ConfirmNoChippedIDAction.NavigateToConfirmAbort -> {
                    navController.navigate(SelectDocDestinations.ConfirmAbort)
                }
            }
        }
    }

    ConfirmNoChippedIDContent(
        onConfirmClick = viewModel::onConfirmClick,
        modifier = modifier,
    )
}

@OptIn(UnstableDesignSystemAPI::class)
@Composable
internal fun ConfirmNoChippedIDContent(
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.background,
    ) {
        LeftAlignedScreen(
            title = { horizontalPadding ->
                GdsHeading(
                    stringResource(ConfirmNoChippedIDConstants.titleId),
                    textAlign = GdsHeadingAlignment.LeftAligned,
                    modifier = Modifier.padding(horizontal = horizontalPadding),
                )
            },
            body = { horizontalPadding ->
                item {
                    GdsBulletedList(
                        persistentListOf(
                            stringResource(R.string.confirm_nochippedid_bullet_1),
                            stringResource(R.string.confirm_nochippedid_bullet_2),
                            stringResource(R.string.confirm_nochippedid_bullet_3),
                            stringResource(R.string.confirm_nochippedid_bullet_4),
                        ),
                        title =
                            BulletedListTitle(
                                text = stringResource(R.string.confirm_nochippedid_body_1),
                                titleType = TitleType.Text,
                            ),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }

                item {
                    Text(
                        text = stringResource(R.string.confirm_nochippedid_body_2),
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                    )
                }
            },
            primaryButton = {
                GdsButton(
                    stringResource(ConfirmNoChippedIDConstants.confirmButtonTextId),
                    buttonType = ButtonType.Primary,
                    onClick = onConfirmClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            },
        )
    }
}

@LightDarkBothLocalesPreview
@Composable
internal fun PreviewConfirmNoChippedIDScreen() {
    GdsTheme {
        ConfirmNoChippedIDContent(
            onConfirmClick = {},
        )
    }
}
