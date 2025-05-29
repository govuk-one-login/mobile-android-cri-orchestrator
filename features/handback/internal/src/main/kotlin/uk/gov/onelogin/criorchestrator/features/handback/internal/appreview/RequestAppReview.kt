package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

fun interface RequestAppReview {
    suspend operator fun invoke()
}
