package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.model

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import uk.gov.idcheck.sdk.IdCheckSdkExitState

enum class ExitStateOption(
    val exitState: IdCheckSdkExitState?,
    val displayName: String = exitState?.id ?: error("please set a display name"),
) {
    None(null, "None"),
    HappyPath(IdCheckSdkExitState.HappyPath),
    ;

    companion object {
        val displayNames: ImmutableList<String>
            get() =
                entries
                    .map {
                        it.displayName
                    }.toPersistentList()
    }
}
