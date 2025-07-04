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
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.button.ButtonType
import uk.gov.android.ui.componentsv2.button.GdsButton
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.list.GdsBulletedList
import uk.gov.android.ui.componentsv2.list.ListItem
import uk.gov.android.ui.componentsv2.list.ListTitle
import uk.gov.android.ui.componentsv2.list.TitleType
import uk.gov.android.ui.patterns.leftalignedscreen.LeftAlignedScreen
import uk.gov.android.ui.theme.m3.GdsTheme
import uk.gov.android.ui.theme.util.UnstableDesignSystemAPI
import uk.gov.onelogin.criorchestrator.features.handback.internalapi.nav.AbortDestinations
import uk.gov.onelogin.criorchestrator.features.selectdoc.internal.R
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
                ConfirmNoChippedIDAction.NavigateToConfirmAbortMobile ->
                    navController.navigate(AbortDestinations.ConfirmAbortMobile)

                ConfirmNoChippedIDAction.NavigateToConfirmAbortDesktop ->
                    navController.navigate(AbortDestinations.ConfirmAbortDesktop)
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
                            ListItem(
                                stringResource(R.string.confirm_nochippedid_bullet_1),
                            ),
                            ListItem(
                                stringResource(R.string.confirm_nochippedid_bullet_2),
                            ),
                            ListItem(
                                stringResource(R.string.confirm_nochippedid_bullet_3),
                            ),
                            ListItem(
                                stringResource(R.string.confirm_nochippedid_bullet_4),
                            ),
                        ),
                        title =
                            ListTitle(
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
                    onClick = dropUnlessResumed { onConfirmClick() },
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
