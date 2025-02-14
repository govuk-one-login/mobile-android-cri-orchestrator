package uk.gov.onelogin.criorchestrator.features.session.internal

import com.squareup.anvil.annotations.ContributesBinding
import uk.gov.android.network.api.ApiResponse
import uk.gov.onelogin.criorchestrator.features.session.internalapi.GetActiveSessionUseCase
import uk.gov.onelogin.criorchestrator.features.session.internalapi.SessionApi
import uk.gov.onelogin.criorchestrator.features.session.internalapi.SessionStore
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.ActivityScope
import uk.gov.onelogin.criorchestrator.libraries.di.scopes.CriOrchestratorScope
import javax.inject.Inject

@ActivityScope
@ContributesBinding(CriOrchestratorScope::class)
class DefaultGetActiveSessionUseCase
    @Inject
    constructor(
        private val sessionApi: SessionApi,
        private val sessionStore: SessionStore,
    ) : GetActiveSessionUseCase {
        override suspend fun execute(): Boolean {
            val isActiveSession: Boolean =
                when (sessionApi.getActiveSession()) {
                    is ApiResponse.Success<*> -> true

                    is ApiResponse.Failure -> false
                    ApiResponse.Loading -> false
                    ApiResponse.Offline -> false
                    else -> false
                }
            sessionStore.write(isActiveSession)
            return sessionStore.read().value
        }
    }
