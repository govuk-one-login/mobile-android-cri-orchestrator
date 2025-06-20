package uk.gov.onelogin.criorchestrator.features.config.internalapi

import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull
import uk.gov.onelogin.criorchestrator.features.config.publicapi.Config
import uk.gov.onelogin.criorchestrator.features.config.publicapi.ConfigKey
import uk.gov.onelogin.criorchestrator.features.config.publicapi.FakeConfig

class FakeConfigStore(
    initialConfig: Config = FakeConfig.create(),
) : ConfigStore {
    private val config: MutableStateFlow<Config> = MutableStateFlow(initialConfig)
    private var readSingleCallCount = 0

    override fun <T : Config.Value> read(key: ConfigKey<T>): Flow<T> =
        config
            .mapNotNull {
                it[key]
            }.distinctUntilChanged()

    override fun <T : Config.Value> readSingle(key: ConfigKey<T>): T {
        readSingleCallCount++
        return config.value[key]
    }

    override fun readAll(): Flow<Config> = config

    override fun writeAll(config: Config) {
        this.config.value = this.config.value.combinedWith(config)
    }

    override fun <T : Config.Value> write(entry: Config.Entry<T>) =
        writeAll(
            Config(
                entries = persistentListOf(entry),
            ),
        )

    fun getReadSingleCount(): Int = readSingleCallCount
}
