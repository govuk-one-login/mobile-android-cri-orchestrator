package uk.gov.onelogin.criorchestrator.features.session.internalapi.domain

fun interface AbortSession {
    suspend operator fun invoke(): Result

    sealed interface Result {
        data object Success : Result

        sealed interface Error : Result {
            data class Unrecoverable(
                val exception: Exception,
            ) : Error

            data object Offline : Error
        }
    }
}
