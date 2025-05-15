package uk.gov.onelogin.criorchestrator.features.handback.internal.appreview

import android.content.Context
import android.widget.Toast
import javax.inject.Inject

class DebugRequestAppReview
    @Inject
    constructor(
        private val context: Context,
    ) : RequestAppReview {
        var hasRequestedReview: Boolean = false

        override suspend fun invoke() {
            hasRequestedReview = true
            Toast.makeText(context, "Debug: app review requested", Toast.LENGTH_LONG).show()
        }
    }
