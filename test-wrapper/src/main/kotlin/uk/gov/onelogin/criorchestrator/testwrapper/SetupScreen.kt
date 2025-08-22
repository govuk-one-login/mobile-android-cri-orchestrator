package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.gov.android.ui.theme.m3.GdsLocalColorScheme
import uk.gov.onelogin.criorchestrator.sdk.sharedapi.CriOrchestratorComponent
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuDialog
import uk.gov.onelogin.criorchestrator.testwrapper.devmenu.DevMenuFloatingActionButton

@Composable
fun SetupScreen(
    onSubUpdateRequest: (String?) -> Unit,
    onStartClick: () -> Unit,
    criOrchestratorComponent: CriOrchestratorComponent,
    modifier: Modifier = Modifier,
) {
    var showDevMenu by remember { mutableStateOf(false) }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            DevMenuFloatingActionButton(
                onClick = { showDevMenu = true },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Setup",
                style = MaterialTheme.typography.headlineMedium,
            )

            var subjectToken by rememberSaveable { mutableStateOf("") }

            LaunchedEffect(subjectToken) {
                onSubUpdateRequest(subjectToken.takeIf { it.isNotBlank() })
            }

            TextField(
                value = subjectToken,
                onValueChange = { subjectToken = it },
                label = {
                    Text("Subject token")
                },
                colors =
                    TextFieldDefaults.colors().copy(
                        focusedContainerColor = GdsLocalColorScheme.current.cardBackground,
                        unfocusedContainerColor = GdsLocalColorScheme.current.cardBackground,
                    ),
            )

            Button(
                onClick = onStartClick,
            ) {
                Text(text = "Start")
            }
        }

        if (showDevMenu) {
            DevMenuDialog(
                criOrchestratorComponent = criOrchestratorComponent,
                onDismissRequest = { showDevMenu = false },
            )
        }
    }
}
