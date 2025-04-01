package uk.gov.onelogin.criorchestrator.testwrapper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.window.Dialog
import uk.gov.android.ui.theme.spacingDouble
import uk.gov.android.ui.theme.spacingSingle

@Composable
fun EnterTextDialog(
    modifier: Modifier = Modifier,
    didEnterValue: (String) -> Unit,
) {
    var showTextEntry by remember { mutableStateOf(false) }

    Button(
        onClick = {
            showTextEntry = true
        },
        modifier = Modifier.padding(spacingDouble),
        content = {
            Text("Start manual journey")
        },
    )

    if (showTextEntry) {
        Dialog(
            onDismissRequest = {
                showTextEntry = false
            },
            content = {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(spacingDouble),
                    shape = RoundedCornerShape(spacingDouble),
                ) {
                    var text: String by remember { mutableStateOf("") }

                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        label = {
                            Text("Enter sub value: ")
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        TextButton(
                            onClick = {
                                showTextEntry = false
                            },
                            modifier = Modifier.padding(spacingSingle),
                        ) {
                            Text("Cancel")
                        }

                        TextButton(
                            onClick = {
                                showTextEntry = false
                                didEnterValue(text)
                            },
                            modifier = Modifier.padding(spacingSingle),
                        ) {
                            Text("Enter")
                        }
                    }
                }
            },
        )
    }
}

@Composable
@PreviewLightDark
internal fun EnterSubDialogPreview() {
    EnterTextDialog(didEnterValue = { })
}