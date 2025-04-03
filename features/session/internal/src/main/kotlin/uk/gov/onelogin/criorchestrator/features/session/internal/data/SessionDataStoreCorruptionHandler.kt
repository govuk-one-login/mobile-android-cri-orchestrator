package uk.gov.onelogin.criorchestrator.features.session.internal.data

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import uk.gov.onelogin.criorchestrator.features.session.internalapi.domain.Session

internal val sessionDataStoreCorruptionHandler =
    ReplaceFileCorruptionHandler<Session?> {
        null
    }
