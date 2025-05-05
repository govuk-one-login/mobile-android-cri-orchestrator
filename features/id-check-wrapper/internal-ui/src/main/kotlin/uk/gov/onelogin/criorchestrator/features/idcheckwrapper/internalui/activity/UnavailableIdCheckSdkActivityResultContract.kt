package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internalui.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import uk.gov.idcheck.sdk.IdCheckSdkExitState
import uk.gov.idcheck.sdk.IdCheckSdkParameters

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
    ): IdCheckSdkExitState = fail()

    private fun fail(): Nothing = error("Can't launch ID Check SDK while loading")
}
