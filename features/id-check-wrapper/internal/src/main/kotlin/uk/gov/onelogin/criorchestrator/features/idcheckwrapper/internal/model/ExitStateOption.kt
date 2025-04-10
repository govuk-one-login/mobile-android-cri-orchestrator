package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import uk.gov.idcheck.sdk.IdCheckSdkExitState

enum class ExitStateOption(
    val exitState: IdCheckSdkExitState?,
    val displayName: String,
) {
    None(displayName = "None"),
    HappyPath(IdCheckSdkExitState.HappyPath),
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
