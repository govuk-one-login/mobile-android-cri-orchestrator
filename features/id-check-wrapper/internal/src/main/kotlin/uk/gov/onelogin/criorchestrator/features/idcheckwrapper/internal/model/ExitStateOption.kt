package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import android.content.Intent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.idcheck.ui.presentation.dialogs.confirmanotherway.ConfirmationAbortedNavModel

/**
 * The set of ID Check SDK results that can be forced for testing.
 */
enum class ExitStateOption(
    val exitState: IdCheckSdkExitState?,
    val displayName: String,
) {
    None(displayName = "None"),
    HappyPath(IdCheckSdkExitState.HappyPath),
    ConfirmAnotherWay(
        IdCheckSdkExitState.ConfirmAnotherWay(STUB_SCREEN_VIEW_NAME),
    ),
    ConfirmationAbortedJourney(
        IdCheckSdkExitState.ConfirmationAbortedJourney(stubConfirmationAbortedNavModel),
    ),
    ConfirmationFailed(IdCheckSdkExitState.ConfirmationFailed),
    FaceScanLimitReached(
        IdCheckSdkExitState.FaceScanLimitReached(
            handoverSessionId = STUB_SESSION_ID,
            readIdSessionId = STUB_SESSION_ID,
        ),
    ),
    UnknownDocumentType(IdCheckSdkExitState.UnknownDocumentType),
    Nowhere(IdCheckSdkExitState.Nowhere),
    ;

    constructor(
        displayName: String,
    ) : this(
        exitState = null,
        displayName = displayName,
    )

    constructor(
        exitState: IdCheckSdkExitState,
    ) : this(
        exitState = exitState,
        displayName = exitState.id,
    )

    companion object {
        val displayNames: ImmutableList<String>
            get() =
                entries
                    .map {
                        it.displayName
                    }.toPersistentList()
    }
}

private const val STUB_SCREEN_VIEW_NAME = "stubScreenViewName"
private const val STUB_SESSION_ID = "stubSessionId"

private val stubConfirmationAbortedNavModel =
    ConfirmationAbortedNavModel(
        section = "section",
        additionalInfoText = "additionalInfoText",
        substringToColour = "substringToColour",
        intentToStart = Intent(),
    )
