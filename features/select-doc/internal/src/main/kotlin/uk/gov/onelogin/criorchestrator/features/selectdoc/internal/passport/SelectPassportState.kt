package uk.gov.onelogin.criorchestrator.features.selectdoc.internal.passport

import kotlinx.collections.immutable.PersistentList

data class SelectPassportState(
    val options: PersistentList<Int>,
)
