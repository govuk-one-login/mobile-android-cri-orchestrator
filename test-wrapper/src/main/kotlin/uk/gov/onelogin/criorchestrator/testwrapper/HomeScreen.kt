package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.gov.onelogin.criorchestrator.features.resume.publicapi.ProveYourIdentityCard
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent

@Composable
fun HomeScreen(
    criOrchestratorComponent: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) = Surface(
    color = MaterialTheme.colorScheme.background,
    modifier =
        modifier
            .fillMaxSize()
            .padding(top = 16.dp),
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        ProveYourIdentityCard(
            component = criOrchestratorComponent,
            modifier = Modifier.padding(horizontal = 16.dp),
        )
    }
}
