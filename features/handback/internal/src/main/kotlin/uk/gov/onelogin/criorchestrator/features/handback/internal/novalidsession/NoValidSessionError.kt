package uk.gov.onelogin.criorchestrator.features.handback.internal.novalidsession

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import kotlinx.collections.immutable.persistentListOf
import uk.gov.android.ui.componentsv2.heading.GdsHeading
import uk.gov.android.ui.componentsv2.heading.GdsHeadingAlignment
import uk.gov.android.ui.componentsv2.images.GdsIcon
import uk.gov.android.ui.patterns.centrealignedscreen.CentreAlignedScreen
import uk.gov.android.ui.patterns.R as patternsR

@SuppressLint("ComposeModifierMissing")
@Suppress("LongMethod")
@Composable
fun NoValidSessionError(parameters: NoValidSessionErrorParameters) {
    parameters.apply {
        val bodyContent = persistentListOf(para1, para2, para3)
        CentreAlignedScreen(
            image = { padding ->
                GdsIcon(
                    image = ImageVector.vectorResource(patternsR.drawable.ic_warning_error),
                    contentDescription = stringResource(patternsR.string.error_icon_description),
                    modifier =
                        Modifier
                            .padding(horizontal = padding)
                            .fillMaxWidth(),
                )
            },
            title = { padding ->
                GdsHeading(
                    text = title,
                    modifier = Modifier.padding(horizontal = padding),
                    textAlign = GdsHeadingAlignment.CenterAligned,
                )
            },
            body = { padding ->
                items(bodyContent.size) { index ->
                    Text(
                        text = bodyContent[index],
                        textAlign = TextAlign.Center,
                        modifier =
                            Modifier
                                .padding(horizontal = padding)
                                .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            },
        )
    }
}
