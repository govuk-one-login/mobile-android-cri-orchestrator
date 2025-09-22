package uk.gov.onelogin.criorchestrator.features.resume.publicapi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier

@Immutable
fun interface ProveYourIdentityEntryPoints {
    @Composable
    fun ProveYourIdentityCard(modifier: Modifier)
}

fun interface ProveYourIdentityEntryPointsProviders {
    fun proveYourIdentityEntryPoints(): ProveYourIdentityEntryPoints
}
