package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import uk.gov.idcheck.sdk.IdCheckSdkActivityResultContract
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.idcheck.sdk.IdCheckSdkParameters

/**
 * This contract is unable to launch intents, but is able to parse intents and transform them into exit states.
 * This is because if the app activity that launches the ID Check SDK dies during the ID Check SDK journey, when
 * the ID Check SDK hands back to the CRI Orchestrator screen, the activity is recreated and thos contract is used
 * by default and so must be able to parse the intent correctly.
 */
internal class UnavailableIdCheckSdkActivityResultContract :
    ActivityResultContract<
        IdCheckSdkParameters,
        IdCheckSdkExitState,
    >() {
    override fun createIntent(
        context: Context,
        input: IdCheckSdkParameters,
    ): Intent = fail()

    override fun parseResult(
        resultCode: Int,
        intent: Intent?,
    ): IdCheckSdkExitState = IdCheckSdkActivityResultContract.defaultParseResultBehaviour().transform(intent)

    private fun fail(): Nothing = error("Can't launch ID Check SDK while loading")
}
