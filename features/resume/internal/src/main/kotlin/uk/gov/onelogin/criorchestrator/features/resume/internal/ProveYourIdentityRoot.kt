package uk.gov.onelogin.criorchestrator.features.resume.internal

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun ProveYourIdentityRoot(
    viewModel: ProveYourIdentityViewModel,
    modifier: Modifier = Modifier,
) {
    ProveYourIdentityUiCard(
        onStartClick = viewModel::start,
        modifier = modifier,
    )
}
