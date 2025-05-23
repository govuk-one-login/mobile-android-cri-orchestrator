package uk.gov.onelogin.criorchestrator.libraries.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainStandardDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher(),
) : TestRule by MainDispatcherRule(testDispatcher)
