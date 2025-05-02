package uk.gov.onelogin.criorchestrator.testwrapper

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubjectTokenRepository
    @Inject
    constructor() {
        var subjectToken: String? = null
    }
