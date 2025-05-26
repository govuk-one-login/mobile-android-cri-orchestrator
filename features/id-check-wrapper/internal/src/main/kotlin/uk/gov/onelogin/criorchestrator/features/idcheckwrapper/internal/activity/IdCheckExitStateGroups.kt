package uk.gov.onelogin.criorchestrator.features.idcheckwrapper.internal.activity

enum class IdCheckExitStateGroups {
    SUCCESS,
    ABORT,
    // DCMAW-13562: Refactor to better represent caught exit states
    FACE_SCAN_LIMIT_REACHED,
}
