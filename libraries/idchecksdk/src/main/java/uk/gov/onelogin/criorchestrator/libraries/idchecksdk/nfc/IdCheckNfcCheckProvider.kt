package uk.gov.onelogin.criorchestrator.libraries.idchecksdk.nfc

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class IdCheckNfcCheckProvider
    @Inject
    constructor() : NfcCheckerProvider {
        override fun getNfcChecker(): NcfCheckerTest = NfcCheckerTestImpl()
    }

fun interface NfcCheckerProvider {
    fun getNfcChecker(): NcfCheckerTest
}

interface NcfCheckerTest {
    fun hasNfc(): Boolean
}

class NfcCheckerTestImpl : NcfCheckerTest {
    override fun hasNfc(): Boolean = false
}
