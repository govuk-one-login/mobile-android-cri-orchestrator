package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeIsSessionResumable : IsSessionResumable {
    val value = MutableStateFlow(false)

    override fun invoke(): Flow<Boolean> = value.asStateFlow()
}
