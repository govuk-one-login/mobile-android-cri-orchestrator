package uk.gov.onelogin.criorchestrator.libraries.idchecksdk.nfc

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorScope
import javax.inject.Inject

@ContributesBinding(CriOrchestratorScope::class)
class FakeNfcChecker
    @Inject
    constructor() : NcfCheckerTest {
        override fun hasNfc(): Boolean = false
    }
