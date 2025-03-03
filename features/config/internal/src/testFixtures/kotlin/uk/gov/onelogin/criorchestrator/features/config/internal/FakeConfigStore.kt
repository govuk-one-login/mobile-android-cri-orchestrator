package uk.gov.onelogin.criorchestrator.features.config.internal

import uk.gov.logging.testdouble.SystemLogger
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigStore

class FakeConfigStore private constructor(
    private val initialConfig: Config,
    private val delegate: InMemoryConfigStore,
) : ConfigStore by delegate {
    constructor(
        config: Config = StubConfig.config,
    ) : this(
        initialConfig = config,
        delegate =
            InMemoryConfigStore(
                logger = SystemLogger(),
                initialConfig = config,
            ),
    )
}
