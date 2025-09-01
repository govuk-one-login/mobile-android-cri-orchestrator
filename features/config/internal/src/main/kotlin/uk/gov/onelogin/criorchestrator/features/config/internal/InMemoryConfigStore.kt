package uk.gov.onelogin.criorchestrator.features.config.internal

import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import dev.zacsweers.metro.binding
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import uk.gov.logging.api.LogTagProvider
import uk.gov.logging.api.Logger
import uk.gov.onelogin.criorchestrator.features.config.internalapi.ConfigStore
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigKey
import uk.gov.onelogin.criorchestrator.libraries.di.CriOrchestratorSingletonScope

@SingleIn(CriOrchestratorSingletonScope::class)
@ContributesBinding(CriOrchestratorSingletonScope::class, binding = binding<ConfigStore>())
class InMemoryConfigStore
    @Inject
    constructor(
        private val logger: Logger,
        initialConfig: Config,
    ) : ConfigStore,
        LogTagProvider {
        private val config: MutableStateFlow<Config> = MutableStateFlow(initialConfig)

        override fun <T : Config.Value> read(key: ConfigKey<T>): Flow<T> =
            config
                .mapNotNull {
                    it[key]
                }.distinctUntilChanged()

        override fun <T : Config.Value> readSingle(key: ConfigKey<T>): T = config.value[key]

        override fun readAll(): Flow<Config> = config

        override fun writeAll(config: Config) {
            this.config.value = this.config.value.combinedWith(config)

            logger.debug(
                tag,
                "Wrote config: $config",
            )
        }

        override fun <T : Config.Value> write(entry: Config.Entry<T>) =
            writeAll(
                Config(
                    entries = persistentListOf(entry),
                ),
            )
    }
